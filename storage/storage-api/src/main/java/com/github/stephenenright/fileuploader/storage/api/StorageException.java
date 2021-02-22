package com.github.stephenenright.fileuploader.storage.api;

public class StorageException extends RuntimeException {
	
	private static final long serialVersionUID = 3871386052270983797L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
