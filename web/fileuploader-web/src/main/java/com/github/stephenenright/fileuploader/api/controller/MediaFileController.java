package com.github.stephenenright.fileuploader.api.controller;

import com.github.stephenenright.fileuploader.api.common.response.ApiResponse;
import com.github.stephenenright.fileuploader.api.controller.delegate.MediaFileControllerDelegate;
import com.github.stephenenright.fileuploader.common.model.request.PagingRequest;
import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import com.github.stephenenright.fileuploader.models.dto.MediaFileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
public class MediaFileController extends BaseController<MediaFile> {

    private final MediaFileControllerDelegate mediaFileDelegate;

    public MediaFileController(MediaFileControllerDelegate mediaFileDelegate) {
        this.mediaFileDelegate = mediaFileDelegate;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<MediaFileDTO>> list(PagingRequest request) {
        return apiListPaged(mediaFileDelegate::list, request);
    }


    @DeleteMapping(path="/{id}")
    public ResponseEntity<ApiResponse<MediaFileDTO>> delete(@PathVariable Long id) {
        return apiDelete(() -> mediaFileDelegate.deleteById(id));
    }



}
