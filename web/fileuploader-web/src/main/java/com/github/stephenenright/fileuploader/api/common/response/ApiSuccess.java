package com.github.stephenenright.fileuploader.api.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSuccess {
    private final String message;
}
