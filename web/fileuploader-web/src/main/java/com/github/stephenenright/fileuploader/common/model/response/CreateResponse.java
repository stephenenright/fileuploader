package com.github.stephenenright.fileuploader.common.model.response;

import com.github.stephenenright.fileuploader.common.model.response.impl.CreateResponseImpl;
import com.github.stephenenright.fileuploader.common.validation.ValidationError;

import java.util.Map;
import java.util.Optional;

public interface CreateResponse<T> {

    Optional<T> getResult();

    Optional<Map<String, ValidationError>> getValidationErrors();

    Optional<String> getErrorMessage();

    Optional<String> getErrorCode();

    Optional<String> getMessage();

    boolean isCreated();


    static <T> CreateResponse<T> createSuccessResponse(T result) {
        return new CreateResponseImpl<>(true, null, null, null, result, null);
    }

    static <T> CreateResponse<T> createSuccessResponse(T result, String message) {
        return new CreateResponseImpl<>(true, message, null, null, result, null);
    }


    static <T> CreateResponse<T> createValidationFailedResponse(Map<String, ValidationError> errors) {
        return new CreateResponseImpl<>(errors);
    }

    static <T> CreateResponse<T> createFailedResponse(String errorMessage) {
        return new CreateResponseImpl<>(errorMessage);
    }


    static <T> CreateResponse<T> createFromResponse(CreateResponse<?> response, T result) {
        return new CreateResponseImpl<>(response.isCreated(), response.getMessage().orElse(null), response.getErrorMessage().orElse(null),
                response.getErrorCode().orElse(null), result, response.getValidationErrors().orElse(null));

    }


}
