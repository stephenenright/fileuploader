package com.github.stephenenright.fileuploader.application.events.listener;

import com.github.stephenenright.fileuploader.application.events.MediaFileDeletedEvent;
import com.github.stephenenright.fileuploader.storage.api.StorageApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class MediaFileEventListener {

    private final StorageApi mediaFileStorage;

    public MediaFileEventListener(@Qualifier("mediaFileStorage") StorageApi mediaFileStorage) {
        this.mediaFileStorage = mediaFileStorage;
    }


    @TransactionalEventListener
    public void handleMediaFileDelete(MediaFileDeletedEvent event) {
        if (log.isInfoEnabled()) {
            log.info(String.format("Deleting from storage media file with id: %s",event.getFile().getId()));
        }

        //TODO retries etc
        mediaFileStorage.delete(null, event.getFile().getName());

    }
}
