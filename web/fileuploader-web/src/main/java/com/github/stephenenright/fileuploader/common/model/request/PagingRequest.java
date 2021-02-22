package com.github.stephenenright.fileuploader.common.model.request;

import lombok.Data;

@Data
public class PagingRequest {

    private int page;
    private int pageSize;

    public PagingRequest() {
        this(0, 20);
    }

    public PagingRequest(int pageNumber, int numResultsPerPage) {
        this.page = pageNumber;
        this.pageSize = numResultsPerPage;
    }


    public static PagingRequest createForNextPage(PagingRequest pagingRequest) {
        return new PagingRequest(pagingRequest.getPage() + 1, pagingRequest.getPageSize());
    }

    public static PagingRequest sanitized(PagingRequest pagingRequest, int maxResultsPerPage, int minResultsPerPage) {
        return new PagingRequest(pagingRequest.page,  Math.min(Math.max(pagingRequest.pageSize, minResultsPerPage), maxResultsPerPage));
    }


}
