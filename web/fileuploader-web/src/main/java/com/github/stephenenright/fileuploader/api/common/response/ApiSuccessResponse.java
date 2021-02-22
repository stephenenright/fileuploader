package com.github.stephenenright.fileuploader.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSuccessResponse<RT> implements ApiResponse<RT> {
    private final ApiSuccess success;
    private final RT data;

}
