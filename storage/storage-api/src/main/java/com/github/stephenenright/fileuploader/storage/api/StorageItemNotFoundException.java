package com.github.stephenenright.fileuploader.storage.api;

public class StorageItemNotFoundException extends StorageException {

	private static final long serialVersionUID = -3002936024554777338L;

	public StorageItemNotFoundException(String message) {
		super(message);
	}

	public StorageItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
