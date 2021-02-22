package com.github.stephenenright.fileuploader.common.model.response.impl;

import com.github.stephenenright.fileuploader.common.model.response.DownloadModelResponse;
import org.springframework.core.io.InputStreamResource;

public class DownloadModelResponseImpl<T> implements DownloadModelResponse<T> {

    private final T model;
    private final InputStreamResource resource;

    public DownloadModelResponseImpl(T model, InputStreamResource resource) {
        this.model = model;
        this.resource = resource;
    }

    public T getModel() {
        return model;
    }

    public InputStreamResource getResource() {
        return resource;
    }


}
