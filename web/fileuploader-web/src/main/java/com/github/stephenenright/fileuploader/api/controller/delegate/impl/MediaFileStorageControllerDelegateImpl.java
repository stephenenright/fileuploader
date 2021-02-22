package com.github.stephenenright.fileuploader.api.controller.delegate.impl;

import com.github.stephenenright.fileuploader.api.controller.delegate.MediaFileStorageControllerDelegate;
import com.github.stephenenright.fileuploader.application.service.MediaFileService;
import com.github.stephenenright.fileuploader.common.model.response.CreateResponse;
import com.github.stephenenright.fileuploader.common.model.response.DownloadModelResponse;
import com.github.stephenenright.fileuploader.common.model.response.GetResponse;
import com.github.stephenenright.fileuploader.common.model.response.impl.DownloadModelResponseImpl;
import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import com.github.stephenenright.fileuploader.models.dto.MediaFileDTO;
import com.github.stephenenright.fileuploader.models.dto.UploadMediaFileDTO;
import com.github.stephenenright.fileuploader.models.dto.converter.MediaFileDTOConverter;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class MediaFileStorageControllerDelegateImpl implements MediaFileStorageControllerDelegate {

    private final MediaFileService mediaFileService;
    private final MediaFileDTOConverter mediaFileDTOConverter;


    public MediaFileStorageControllerDelegateImpl(MediaFileService mediaFileService,
                                                  MediaFileDTOConverter mediaFileDTOConverter) {
        this.mediaFileService = mediaFileService;
        this.mediaFileDTOConverter = mediaFileDTOConverter;
    }


    @Override
    public CreateResponse<MediaFileDTO> uploadFile(UploadMediaFileDTO request) {
        CreateResponse<MediaFile> uploadResponse = mediaFileService.upload(request);

        if (uploadResponse.isCreated()) {
            return CreateResponse.createFromResponse(uploadResponse, mediaFileDTOConverter.fromModelToDTO(
                    Objects.requireNonNull(uploadResponse.getResult().orElse(null))));
        }


        return CreateResponse.createFromResponse(uploadResponse, null);
    }


    @Override
    public GetResponse<DownloadModelResponse<MediaFileDTO>> downloadFile(Long fileId) {
        Optional<DownloadModelResponse<MediaFile>> downloadResponse = mediaFileService.download(fileId);

        if (downloadResponse.isEmpty()) {
            return GetResponse.createNotFoundResponse();
        }

        DownloadModelResponse<MediaFile> downloadModel = downloadResponse.get();
        return GetResponse.createSuccessResponse(new DownloadModelResponseImpl<>(
                mediaFileDTOConverter.fromModelToDTO(downloadModel.getModel()), downloadModel.getResource()));

    }
}
