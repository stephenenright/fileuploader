package com.github.stephenenright.fileuploader.common.model.response.impl;

import com.github.stephenenright.fileuploader.common.model.response.DeleteResponse;

import java.util.Optional;

public class DeleteResponseImpl<T> implements DeleteResponse<T> {

    private final boolean notFound;
    private final String errorMessage;
    private final boolean deleted;
    private final String message;
    private final String errorCode;
    private final T result;

    public DeleteResponseImpl(boolean deleted) {
        this(deleted, false);

    }

    public DeleteResponseImpl(boolean deleted, boolean notFound) {
        this(deleted, null, notFound, null, null, null);
    }


    public DeleteResponseImpl(boolean deleted, String errorMessage) {
        this(deleted, errorMessage, null);
    }

    public DeleteResponseImpl(boolean deleted, String errorMessage, String errorCode) {
        this(deleted, null, false, errorMessage, errorCode, null);
    }

    public DeleteResponseImpl(boolean deleted, String message, boolean notFound, String errorMessage, String errorCode, T result) {
        this.deleted = deleted;
        this.message = message;
        this.notFound = notFound;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.result = result;
    }


    @Override
    public boolean isNotFound() {
        return notFound;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
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
}
