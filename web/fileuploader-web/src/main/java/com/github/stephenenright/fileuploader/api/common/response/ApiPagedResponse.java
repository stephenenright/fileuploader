package com.github.stephenenright.fileuploader.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class ApiPagedResponse<RT> implements ApiResponse<RT> {
    private final long totalResults;
    private final int pageNumber;
    private final List<RT> data;
    private final int numberOfItemsPerPage;
    private final long numberOfPages;

}
