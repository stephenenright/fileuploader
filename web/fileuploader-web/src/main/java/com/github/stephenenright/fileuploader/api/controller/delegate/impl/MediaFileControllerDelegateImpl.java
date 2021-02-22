package com.github.stephenenright.fileuploader.api.controller.delegate.impl;

import com.github.stephenenright.fileuploader.api.controller.delegate.MediaFileControllerDelegate;
import com.github.stephenenright.fileuploader.application.service.MediaFileService;
import com.github.stephenenright.fileuploader.common.model.request.PagingRequest;
import com.github.stephenenright.fileuploader.common.model.response.DeleteResponse;
import com.github.stephenenright.fileuploader.common.model.response.GetResponse;
import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import com.github.stephenenright.fileuploader.models.dto.MediaFileDTO;
import com.github.stephenenright.fileuploader.models.dto.PageResultDTO;
import com.github.stephenenright.fileuploader.models.dto.converter.MediaFileDTOConverter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MediaFileControllerDelegateImpl implements MediaFileControllerDelegate {

    private final MediaFileService mediaFileService;
    private final MediaFileDTOConverter mediaFileDTOConverter;

    public MediaFileControllerDelegateImpl(
            MediaFileService mediaFileService,
            MediaFileDTOConverter mediaFileDTOConverter) {
        this.mediaFileService = mediaFileService;
        this.mediaFileDTOConverter = mediaFileDTOConverter;
    }

    @Override
    public GetResponse<PageResultDTO<MediaFileDTO>> list(PagingRequest pagingRequest) {
        Page<MediaFile> resultPage = mediaFileService.list(pagingRequest);
        return GetResponse.createSuccessResponse(PageResultDTO.createFrom(resultPage, mediaFileDTOConverter));
    }

    @Override
    public DeleteResponse<MediaFileDTO> deleteById(Long id) {
        Optional<MediaFile> deletedFile = mediaFileService.deleteById(id);

        if (deletedFile.isEmpty()) {
            return DeleteResponse.createNotFoundResponse();
        }

        return DeleteResponse.createSuccessResponse(
                mediaFileDTOConverter.fromModelToDTO(deletedFile.get()));
    }
}
