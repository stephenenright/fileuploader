package com.github.stephenenright.fileuploader.configuration;

import com.github.stephenenright.fileuploader.configuration.settings.MediaFileStorageConfigurationSettings;
import com.github.stephenenright.fileuploader.storage.api.StorageApi;
import com.github.stephenenright.fileuploader.storage.file.FileStorageImpl;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FileUploaderWebConfiguration {

    @Bean("mediaFileStorage")
    public StorageApi mediaFileStorage(MediaFileStorageConfigurationSettings fileStorageSettings) {
        if (log.isInfoEnabled()) {
            log.info(String.format("Storing files in directory: %s", fileStorageSettings.getFileStorage().getDirectory()));
        }

        return new FileStorageImpl(fileStorageSettings.getFileStorage().getDirectory());
    }
}
