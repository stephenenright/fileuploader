package com.github.stephenenright.fileuploader.api.controller;

import com.github.stephenenright.fileuploader.api.common.response.ApiResponse;
import com.github.stephenenright.fileuploader.api.common.response.utils.ApiResponseUtils;
import com.github.stephenenright.fileuploader.api.controller.delegate.MediaFileStorageControllerDelegate;
import com.github.stephenenright.fileuploader.common.model.response.DownloadModelResponse;
import com.github.stephenenright.fileuploader.common.model.response.GetResponse;
import com.github.stephenenright.fileuploader.common.utils.FileNameUtils;
import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import com.github.stephenenright.fileuploader.models.dto.MediaFileDTO;
import com.github.stephenenright.fileuploader.models.dto.UploadMediaFileDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/api/storage/files")
public class MediaFileStorageController extends BaseController<MediaFile> {

    private final MediaFileStorageControllerDelegate mediaFileStorageDelegate;

    public MediaFileStorageController(MediaFileStorageControllerDelegate mediaFileStorageDelegate) {
        this.mediaFileStorageDelegate = mediaFileStorageDelegate;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<MediaFileDTO>> upload(@ModelAttribute @Valid UploadMediaFileDTO request, BindingResult bindingResult) {
        return apiCreate(() -> mediaFileStorageDelegate.uploadFile(request), bindingResult);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> download(@PathVariable("id") Long id) {
        GetResponse<DownloadModelResponse<MediaFileDTO>> downloadResponse = mediaFileStorageDelegate.downloadFile(id);

        if (downloadResponse.isNotFoundError() || downloadResponse.getResult().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (downloadResponse.isOk()) {
            final DownloadModelResponse<MediaFileDTO> downloadModel = downloadResponse.getResult().get();

            return new ResponseEntity<>(downloadModel.getResource(),
                    getDownloadHeaders(downloadModel.getModel()),
                    HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseUtils.createErrorResponse(downloadResponse));
    }


    private HttpHeaders getDownloadHeaders(MediaFileDTO mediaFileDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(mediaFileDTO.getSize());
        headers.setContentDispositionFormData("file",
                FileNameUtils.appendExtension(mediaFileDTO.getOriginalName(), mediaFileDTO.getExtension()));
        return headers;
    }
}
