package com.github.stephenenright.fileuploader.common.model.response;

import org.springframework.core.io.InputStreamResource;

public interface DownloadModelResponse<T> {

    T getModel();

    InputStreamResource getResource();

}
