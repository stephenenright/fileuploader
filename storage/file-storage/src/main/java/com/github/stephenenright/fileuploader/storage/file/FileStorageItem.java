package com.github.stephenenright.fileuploader.storage.file;

import com.github.stephenenright.fileuploader.storage.api.StorageItem;

public class FileStorageItem implements StorageItem {

	private final String name;
	private final Long contentLength;
	
	public FileStorageItem(String name, Long contentLength) {
		this.name = name;
		this.contentLength = contentLength;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Long getContentLength() {
		return contentLength;
	}
}
