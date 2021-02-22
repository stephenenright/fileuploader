package com.github.stephenenright.fileuploader.storage.api;

public abstract class BaseStorageProvider implements StorageApi {
	
	@SuppressWarnings("unchecked")
	@Override
	public <RT> RT getProvider(Class<RT> cls) {
		return (RT) this;
	}

}
