package com.github.stephenenright.fileuploader.application.service;

import com.github.stephenenright.fileuploader.common.model.request.PagingRequest;
import com.github.stephenenright.fileuploader.common.model.response.CreateResponse;
import com.github.stephenenright.fileuploader.common.model.response.DownloadModelResponse;
import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import com.github.stephenenright.fileuploader.models.dto.UploadMediaFileDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface MediaFileService {

    //TODO ADD SEARCH CRITERIA AND SORTING
    Page<MediaFile> list(PagingRequest pagingRequest);

    Optional<MediaFile> findById(Long id);

    Optional<DownloadModelResponse<MediaFile>> download(Long fileId);

    CreateResponse<MediaFile> upload(UploadMediaFileDTO request);

    Optional<MediaFile> deleteById(Long fileId);

}
