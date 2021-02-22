package com.github.stephenenright.fileuploader.application.repository;

import com.github.stephenenright.fileuploader.application.respository.MediaFileRepository;
import com.github.stephenenright.fileuploader.common.utils.UUIDUtils;
import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MediaFileRepositoryIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MediaFileRepository mediaFileRepository;


    @Test
    public void save() {
        final MediaFile mediaFile = MediaFile.builder()
                .title("File 1")
                .description("Description 1")
                .name(UUIDUtils.createUUIDWithoutSpaces())
                .originalName("file1.txt")
                .size(300L)
                .extension("txt")
                .creationDate(ZonedDateTime.now())
                .build();

        final MediaFile mediaFileSaved = mediaFileRepository.save(mediaFile);

        assertNotNull(mediaFileSaved);
        assertNotNull(mediaFileSaved.getId());
        assertTrue(mediaFileSaved.getId() > 0);
    }

    @Test
    public void deleteById() {
        final MediaFile mediaFile = MediaFile.builder()
                .title("File 1")
                .description("Description 1")
                .name(UUIDUtils.createUUIDWithoutSpaces())
                .originalName("file1.txt")
                .size(300L)
                .extension("txt")
                .creationDate(ZonedDateTime.now())
                .build();

        entityManager.persist(mediaFile);
        entityManager.flush();

        assertNotNull(entityManager.find(MediaFile.class, mediaFile.getId()));

        mediaFileRepository.deleteById(mediaFile.getId());

        assertNull(entityManager.find(MediaFile.class, mediaFile.getId()));
    }



}
