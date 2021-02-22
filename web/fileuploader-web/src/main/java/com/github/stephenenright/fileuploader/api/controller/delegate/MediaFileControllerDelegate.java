package com.github.stephenenright.fileuploader.api.controller.delegate;

import com.github.stephenenright.fileuploader.common.model.request.PagingRequest;
import com.github.stephenenright.fileuploader.common.model.response.DeleteResponse;
import com.github.stephenenright.fileuploader.common.model.response.GetResponse;
import com.github.stephenenright.fileuploader.models.dto.MediaFileDTO;
import com.github.stephenenright.fileuploader.models.dto.PageResultDTO;

public interface MediaFileControllerDelegate {

    GetResponse<PageResultDTO<MediaFileDTO>> list(PagingRequest pagingRequest);

    DeleteResponse<MediaFileDTO> deleteById(Long id);



}
