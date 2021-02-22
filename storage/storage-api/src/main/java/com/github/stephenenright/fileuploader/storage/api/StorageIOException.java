package com.github.stephenenright.fileuploader.storage.api;

public class StorageIOException extends StorageException {
	
	private static final long serialVersionUID = 2795920215134235226L;

	public StorageIOException(String message) {
		super(message);
	}

	public StorageIOException(String message, Throwable cause) {
		super(message, cause);
	}
}
