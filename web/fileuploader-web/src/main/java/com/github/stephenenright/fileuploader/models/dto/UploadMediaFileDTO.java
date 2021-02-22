package com.github.stephenenright.fileuploader.models.dto;

import com.github.stephenenright.fileuploader.common.validation.annotation.ISODateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Data
public class UploadMediaFileDTO {

    @NotEmpty
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String description;

    @NotEmpty
    @ISODateTime
    private String creationDate;

    @NotNull
    private MultipartFile file;
}
