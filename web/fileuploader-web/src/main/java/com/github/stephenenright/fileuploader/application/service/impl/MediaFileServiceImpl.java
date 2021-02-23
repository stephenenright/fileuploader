package com.github.stephenenright.fileuploader.application.service.impl;

import com.github.stephenenright.fileuploader.application.events.MediaFileDeletedEvent;
import com.github.stephenenright.fileuploader.application.respository.MediaFileRepository;
import com.github.stephenenright.fileuploader.application.service.MediaFileService;
import com.github.stephenenright.fileuploader.common.model.request.PagingRequest;
import com.github.stephenenright.fileuploader.common.model.response.CreateResponse;
import com.github.stephenenright.fileuploader.common.model.response.DownloadModelResponse;
import com.github.stephenenright.fileuploader.common.model.response.impl.DownloadModelResponseImpl;
import com.github.stephenenright.fileuploader.common.utils.StringUtils;
import com.github.stephenenright.fileuploader.common.utils.UUIDUtils;
import com.github.stephenenright.fileuploader.common.utils.ZonedDateTimeUtils;
import com.github.stephenenright.fileuploader.common.validation.ValidationErrorBuilder;
import com.github.stephenenright.fileuploader.configuration.settings.MediaFileStorageConfigurationSettings;
import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import com.github.stephenenright.fileuploader.models.dto.UploadMediaFileDTO;
import com.github.stephenenright.fileuploader.storage.api.StorageApi;
import com.github.stephenenright.fileuploader.storage.api.StorageItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.file.Matcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
public class MediaFileServiceImpl implements MediaFileService {


    private final MediaFileRepository mediaFileRepository;
    private final MediaFileStorageConfigurationSettings mediaFileSettings;
    private final StorageApi mediaFileStorage;
    private final ApplicationEventPublisher applicationEventPublisher;


    public MediaFileServiceImpl(@Qualifier("mediaFileStorage") StorageApi mediaFileStorage,
                                MediaFileStorageConfigurationSettings mediaFileSettings,
                                MediaFileRepository mediaFileRepository,
                                ApplicationEventPublisher applicationEventPublisher) {
        this.mediaFileRepository = mediaFileRepository;
        this.mediaFileStorage = mediaFileStorage;
        this.mediaFileSettings = mediaFileSettings;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public Page<MediaFile> list(PagingRequest pagingRequest) {
        Sort sort = Sort.by(Sort.Order.desc("creationDate"));
        PageRequest pageRequest = PageRequest.of(
                pagingRequest.getPage(),
                pagingRequest.getPageSize(), sort);

        return mediaFileRepository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<MediaFile> findById(Long id) {
        return mediaFileRepository.findById(id);
    }


    @Override
    public Optional<DownloadModelResponse<MediaFile>> download(Long fileId) {

        final Optional<MediaFile> foundMediaFile = findById(fileId);

        if (foundMediaFile.isEmpty()) {
            return Optional.empty();
        }

        final MediaFile mediaFile = foundMediaFile.get();
        final InputStream is = mediaFileStorage.get(null, mediaFile.getName());

        if (is == null) {
            return Optional.empty();
        }


        return Optional.of(new DownloadModelResponseImpl<>(mediaFile, new InputStreamResource(is)));
    }

    @Override
    public CreateResponse<MediaFile> upload(UploadMediaFileDTO request) {
        ValidationErrorBuilder errorBuilder = new ValidationErrorBuilder();

        if (request.getFile() == null) {
            errorBuilder.addRequired("file");
            return CreateResponse.createValidationFailedResponse(errorBuilder.getErrors());
        }

        Optional<ZonedDateTime> parsedZonedDate = ZonedDateTimeUtils.parseISODateTimeAsUtc(request.getCreationDate());

        if (parsedZonedDate.isEmpty()) {
            errorBuilder.addError("creationDate", "A valid date time is required");
            return CreateResponse.createValidationFailedResponse(errorBuilder.getErrors());
        }

        final ZonedDateTime creationDate = parsedZonedDate.get();

        if (creationDate.isAfter(ZonedDateTimeUtils.getNowUtc())) {
            errorBuilder.addError("creationDate", "cannot be in the future");
            return CreateResponse.createValidationFailedResponse(errorBuilder.getErrors());
        }


        final MultipartFile fileToUpload = request.getFile();
        final String originalName = fileToUpload.getOriginalFilename();
        final String fileExt = FilenameUtils.getExtension(originalName);

        if (StringUtils.isNullOrEmpty(fileExt)) {
            errorBuilder.addError("file", "A valid file is required");
            return CreateResponse.createValidationFailedResponse(errorBuilder.getErrors());
        }

        if (StringUtils.isNotNullAndNotEmpty(mediaFileSettings.getAllowedExtValidator())) {
            if (!Pattern.matches(mediaFileSettings.getAllowedExtValidator(),
                    fileExt)) {
                //TODO improve error message with supported extensions
                errorBuilder.addError("file", "File type is not supported");
                return CreateResponse.createValidationFailedResponse(errorBuilder.getErrors());
            }
        }

        final String fileName = findAvailableFileName(fileExt);
        StorageItem item;

        try {
            item = mediaFileStorage.save(null, fileName, fileToUpload.getInputStream(), new HashMap<>());
        } catch (IOException ioe) {
            //TODO use better exception type
            throw new RuntimeException("Unable to upload media file", ioe);
        }

        MediaFile mediaFile = MediaFile.builder()
                .originalName(originalName)
                .name(item.getName())
                .title(request.getTitle())
                .description(request.getDescription())
                .extension(fileExt)
                .size(item.getContentLength())
                .creationDate(creationDate)
                .build();

        try {
            mediaFile = mediaFileRepository.save(mediaFile);
        } catch (Exception e) {
            //TODO HANDLE RETRY ON FAILURE MAYBE SEND THIS TO A QUEUE ETC
            mediaFileStorage.delete(null, item.getName());

            //TODO use better exception type
            throw new RuntimeException("Unable to save media file to db", e);
        }

        return CreateResponse.createSuccessResponse(mediaFile);
    }

    //Transactional applied here as we dont want to write a file to storage (could be an expensive operation) in a transaction for obvious reasons
    @Transactional
    public MediaFile save(MediaFile mediaFile) {
        return mediaFileRepository.save(mediaFile);
    }

    private String findAvailableFileName(final String fileExt) {
        String fileName;
        do {
            fileName = createFileName(fileExt);

        } while (mediaFileStorage.exists(null, fileName));

        return fileName;
    }

    private String createFileName(final String fileExt) {
        if (mediaFileSettings.isUseFileExt()) {
            StringBuilder builder = new StringBuilder();
            builder.append(UUIDUtils.createUUIDWithoutSpaces());
            builder.append(".");
            builder.append(fileExt);
            return builder.toString();
        }

        return UUIDUtils.createUUIDWithoutSpaces();
    }


    @Override
    @Transactional
    public Optional<MediaFile> deleteById(final Long fileId) {
        Optional<MediaFile> foundMediaFile = findById(fileId);

        if (foundMediaFile.isEmpty()) {
            return foundMediaFile;
        }

        mediaFileRepository.deleteById(fileId);
        applicationEventPublisher.publishEvent(MediaFileDeletedEvent.builder().file(foundMediaFile.get()).build());

        return foundMediaFile;
    }
}


