package com.github.stephenenright.fileuploader.common.model.response;

import com.github.stephenenright.fileuploader.common.model.response.impl.DeleteResponseImpl;

import java.util.Optional;

public interface DeleteResponse<T> {

    boolean isNotFound();

    boolean isDeleted();

    Optional<String> getMessage();

    Optional<String> getErrorMessage();

    Optional<String> getErrorCode();

    Optional<T> getResult();


    static <T> DeleteResponse<T> createSuccessResponse() {
        return new DeleteResponseImpl<>(true);
    }

    static <T> DeleteResponse<T> createSuccessResponse(T result) {
        return new DeleteResponseImpl<>(true, null, false, null, null, result);
    }

    static <T> DeleteResponse<T> createSuccessResponse(T result, String message) {
        return new DeleteResponseImpl<>(true, message, false, null, null, result);
    }

    static <T> DeleteResponse<T> createFailureResponse() {
        return new DeleteResponseImpl<>(false);
    }

    static <T> DeleteResponse<T> createNotFoundResponse() {
        return new DeleteResponseImpl<>(false, true);
    }

}




