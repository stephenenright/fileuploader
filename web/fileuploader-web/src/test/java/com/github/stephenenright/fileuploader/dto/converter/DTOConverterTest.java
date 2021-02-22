package com.github.stephenenright.fileuploader.dto.converter;

import com.github.stephenenright.fileuploader.common.utils.UUIDUtils;
import com.github.stephenenright.fileuploader.configuration.settings.MediaFileStorageConfigurationSettings;
import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import com.github.stephenenright.fileuploader.models.dto.MediaFileDTO;
import com.github.stephenenright.fileuploader.models.dto.converter.MediaFileDTOConverter;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DTOConverterTest {

    @Test
    public void fromModelToDTO() {
        MediaFileStorageConfigurationSettings settings = new MediaFileStorageConfigurationSettings();
        settings.setDownloadUrlPattern("/api/storage/files/%s");
        MediaFileDTOConverter converter = new MediaFileDTOConverter(settings);

        MediaFile mediaFile = MediaFile.builder()
                .id(1L)
                .title("File 1")
                .description("Description 1")
                .extension("txt")
                .originalName("file1.txt")
                .name(UUIDUtils.createUUIDWithoutSpaces())
                .size(300L)
                .creationDate(ZonedDateTime.now())
                .build();

        MediaFileDTO mediaFileDTO = converter.fromModelToDTO(mediaFile);

        assertNotNull(mediaFileDTO);
        assertEquals(mediaFile.getId(), mediaFileDTO.getId());
        assertEquals(mediaFile.getTitle(), mediaFileDTO.getTitle());
        assertEquals(mediaFile.getDescription(), mediaFileDTO.getDescription());
        assertEquals(mediaFile.getExtension(), mediaFileDTO.getExtension());
        assertEquals(mediaFile.getOriginalName(), mediaFileDTO.getOriginalName());
        assertEquals(mediaFile.getSize(), mediaFileDTO.getSize());
        assertEquals("/api/storage/files/1", mediaFileDTO.getDownloadUrl());
    }

}
