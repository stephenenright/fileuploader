package com.github.stephenenright.fileuploader.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.stephenenright.fileuploader.common.validation.ValidationError;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@SuperBuilder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private final String message;
    private final String errorCode;
    private final Map<String, ValidationError> validationErrors;


}
