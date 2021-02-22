package com.github.stephenenright.fileuploader.models.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MediaFileDTO {

    private Long id;
    private String originalName;
    private String downloadUrl;
    private String description;
    private String title;
    private String extension;
    private Long size;
    private Long creationDate;


}
