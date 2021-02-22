package com.github.stephenenright.fileuploader.models.dto.converter;

import com.github.stephenenright.fileuploader.common.utils.ZonedDateTimeUtils;
import com.github.stephenenright.fileuploader.configuration.settings.MediaFileStorageConfigurationSettings;
import com.github.stephenenright.fileuploader.models.dto.MediaFileDTO;
import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import org.springframework.stereotype.Component;

@Component
public class MediaFileDTOConverter implements DTOConverter<MediaFile, MediaFileDTO> {

    private final MediaFileStorageConfigurationSettings mediaFileSettings;

    public MediaFileDTOConverter(MediaFileStorageConfigurationSettings mediaFileSettings) {
        this.mediaFileSettings = mediaFileSettings;
    }


    @Override
    public MediaFileDTO fromModelToDTO(MediaFile model, Object... additionalParams) {
        return MediaFileDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .creationDate(ZonedDateTimeUtils.toMillis(model.getCreationDate()))
                .description(model.getDescription())
                .extension(model.getExtension())
                .originalName(model.getOriginalName())
                .size(model.getSize())
                .downloadUrl(model.createDownloadUrl(mediaFileSettings.getDownloadUrlPattern()))
                .build();

    }
}
