package com.github.stephenenright.fileuploader.api.common.functions;

import com.github.stephenenright.fileuploader.common.model.request.PagingRequest;
import com.github.stephenenright.fileuploader.common.model.response.CreateResponse;
import com.github.stephenenright.fileuploader.common.model.response.DeleteResponse;
import com.github.stephenenright.fileuploader.common.model.response.GetResponse;
import com.github.stephenenright.fileuploader.models.dto.PageResultDTO;
import org.springframework.data.domain.Page;

public abstract class ApiFunctions {

    public interface ListPagedApiCall<RT> {
        GetResponse<PageResultDTO<RT>> execute(PagingRequest pagingRequest);
    }

    public interface CreateApiCall<RT> {
        CreateResponse<RT> execute();
    }

    public interface DeleteApiApiCall<RT> {
        DeleteResponse<RT> execute();
    }


}
