package com.github.stephenenright.fileuploader.application.events;

import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MediaFileDeletedEvent {
    private MediaFile file;
}

