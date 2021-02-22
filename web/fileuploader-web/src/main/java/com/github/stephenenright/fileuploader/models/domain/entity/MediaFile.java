package com.github.stephenenright.fileuploader.models.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="media_file")
@Entity
public class MediaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String name;

    @Column(name="original_name")
    private String originalName;

    private String description;

    @Column(name="creation_date")
    private ZonedDateTime creationDate;

    private String extension;

    private Long size;


    public String createDownloadUrl(String downloadUrlTemplate) {
        return String.format(downloadUrlTemplate, id);
    }


}
