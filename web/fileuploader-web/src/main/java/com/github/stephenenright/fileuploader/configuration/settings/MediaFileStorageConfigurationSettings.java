package com.github.stephenenright.fileuploader.configuration.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "file-uploader.media.media-files")
public class MediaFileStorageConfigurationSettings {

	private String allowedExtValidator;
	private boolean useFileExt;
	private String downloadUrlPattern;
	private FileStorageSettings fileStorage;

    public FileStorageSettings getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(FileStorageSettings fileStorage) {
        this.fileStorage = fileStorage;
    }

	public String getDownloadUrlPattern() {
		return downloadUrlPattern;
	}

	public void setDownloadUrlPattern(String downloadUrlPattern) {
		this.downloadUrlPattern = downloadUrlPattern;
	}

	public boolean isUseFileExt() {
		return useFileExt;
	}

	public void setUseFileExt(boolean useFileExt) {
		this.useFileExt = useFileExt;
	}

	public String getAllowedExtValidator() {
		return allowedExtValidator;
	}

	public void setAllowedExtValidator(String allowedExtValidator) {
		this.allowedExtValidator = allowedExtValidator;
	}
}
