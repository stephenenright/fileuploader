package com.github.stephenenright.fileuploader.common.model.response.impl;

import com.github.stephenenright.fileuploader.common.model.response.CreateResponse;
import com.github.stephenenright.fileuploader.common.validation.ValidationError;

import java.util.Map;
import java.util.Optional;

public class CreateResponseImpl<T> implements CreateResponse<T> {

    private final T result;
    private final boolean created;
    private final String message;
    private final Map<String, ValidationError> validationErrors;
    private final String errorMessage;
    private final String errorCode;

    public CreateResponseImpl(String errorMessage) {
        this(false, errorMessage);
    }

    public CreateResponseImpl(Map<String, ValidationError> validationErrors) {
        this(false, null,null, null, null, validationErrors);
    }

    public CreateResponseImpl(boolean created) {
        this(created, null,null, null, null, null);
    }

    public CreateResponseImpl(boolean created, String errorMessage) {
        this(created, null,errorMessage, null, null, null);
    }

    public CreateResponseImpl(boolean created, T result) {
        this(created, null, null, null, result, null);
    }

    public CreateResponseImpl(boolean created, String message, String errorMessage, String errorCode, T result, Map<String, ValidationError> validationErrors) {
        this.created = created;
        this.message = message;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.result = result;
        this.validationErrors = validationErrors;
    }

    @Override
    public Optional<Map<String, ValidationError>> getValidationErrors() {
        return Optional.ofNullable(validationErrors);
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    @Override
    public Optional<String> getErrorCode() {
        return Optional.ofNullable(errorCode);
    }

    @Override
    public Optional<T> getResult() {
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }

    @Override
    public boolean isCreated() {
        return created;
    }
}
