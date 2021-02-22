package com.github.stephenenright.fileuploader.common.validation;

import com.github.stephenenright.fileuploader.common.messages.ErrorMessages;

import java.util.HashMap;
import java.util.Map;

public class ValidationErrorBuilder {

    private Map<String,ValidationError> validationErrors = new HashMap<>();


    public ValidationErrorBuilder addRequired(String key) {
        validationErrors.put(key, new ValidationErrorStandard(key, ErrorMessages.ERROR_REQUIRED));
        return this;
    }

    public ValidationErrorBuilder addError(String key, String message) {
        validationErrors.put(key, new ValidationErrorStandard(key, message));
        return this;
    }

    public Map<String,ValidationError> getErrors() {
        return validationErrors;
    }
}
