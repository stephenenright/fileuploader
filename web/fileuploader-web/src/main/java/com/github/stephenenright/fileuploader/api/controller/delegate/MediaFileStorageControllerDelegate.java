package com.github.stephenenright.fileuploader.api.controller.delegate;

import com.github.stephenenright.fileuploader.models.dto.UploadMediaFileDTO;
import com.github.stephenenright.fileuploader.models.dto.MediaFileDTO;
import com.github.stephenenright.fileuploader.common.model.response.CreateResponse;
import com.github.stephenenright.fileuploader.common.model.response.DownloadModelResponse;
import com.github.stephenenright.fileuploader.common.model.response.GetResponse;

import java.util.Map;

public interface MediaFileStorageControllerDelegate {

    CreateResponse<MediaFileDTO> uploadFile(UploadMediaFileDTO request);

    GetResponse<DownloadModelResponse<MediaFileDTO>> downloadFile(Long fileId);


}
