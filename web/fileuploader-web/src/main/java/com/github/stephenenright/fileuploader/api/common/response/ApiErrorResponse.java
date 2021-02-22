package com.github.stephenenright.fileuploader.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse<RT> implements ApiResponse<RT> {
    private final ApiError error;
    private final RT data;
}
