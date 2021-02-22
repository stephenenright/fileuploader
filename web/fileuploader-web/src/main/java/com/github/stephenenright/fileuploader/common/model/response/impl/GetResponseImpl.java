package com.github.stephenenright.fileuploader.common.model.response.impl;

import com.github.stephenenright.fileuploader.common.model.response.GetResponse;
import com.github.stephenenright.fileuploader.common.utils.StringUtils;
import com.github.stephenenright.fileuploader.common.validation.ValidationError;

import java.util.Map;
import java.util.Optional;

public class GetResponseImpl<RT> implements GetResponse<RT> {

    private final Map<String, ValidationError> validationErrors;
    private final String errorMessage;
    private final String errorCode;
    private final RT result;
    private final boolean notFoundError;

    public GetResponseImpl(String errorMessage) {
        this(false, errorMessage);
    }

    public GetResponseImpl(Map<String,ValidationError> validationErrors) {
        this(false, null, null,null,validationErrors);
    }

    public GetResponseImpl(boolean notFoundError, String errorMessage) {
        this(false,errorMessage, null);
    }

    public GetResponseImpl(boolean notFoundError, String errorMessage, String errorCode) {
        this(notFoundError, errorMessage, errorCode, null,null);
    }

    public GetResponseImpl(boolean notFoundError, String errorMessage, String errorCode, RT result,
                           Map<String,ValidationError> validationErrors) {
        this.notFoundError = notFoundError;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.result = result;
        this.validationErrors = validationErrors;
    }


    @Override
    public boolean isOk() {
        return !isValidationErrors() &&  !StringUtils.isNotNullAndNotEmpty(errorMessage) && !notFoundError;
    }

    @Override
    public boolean isNotFoundError() {
        return notFoundError;
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
    public Optional<RT> getResult() {
        return Optional.ofNullable(result);
    }

    private boolean isValidationErrors() {
        return validationErrors !=null && validationErrors.size() > 0;
    }
}
