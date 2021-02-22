package com.github.stephenenright.fileuploader.application.respository;

import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MediaFileRepository extends JpaRepository<MediaFile, Long>, JpaSpecificationExecutor<MediaFile> {
}
