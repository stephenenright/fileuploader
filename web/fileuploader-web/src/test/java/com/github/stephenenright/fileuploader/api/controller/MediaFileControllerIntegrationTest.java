package com.github.stephenenright.fileuploader.api.controller;


import com.github.stephenenright.fileuploader.common.utils.UUIDUtils;
import com.github.stephenenright.fileuploader.models.domain.entity.MediaFile;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MediaFileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    private List<MediaFile> mediaFileList;


    @BeforeEach
    public void setUp() {
        mediaFileList = new LinkedList<>();

        for (int i = 1; i <= 40; i++) {
            MediaFile mediaFile = MediaFile.builder()
                    .extension("txt")
                    .creationDate(ZonedDateTime.now())
                    .size(300L)
                    .originalName("file" + i + ".txt")
                    .name(UUIDUtils.createUUIDWithoutSpaces())
                    .description("File Description " + i)
                    .title("File Title " + i)
                    .build();

            entityManager.persist(mediaFile);
            mediaFileList.add(mediaFile);
        }

    }

    @Test
    public void upload() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is a file".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/storage/files").file(file)
                .param("title", "File 1")
                .param("creationDate", "2021-02-15T15:19:21Z"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void upload_whenValidationFailed_statusBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/storage/files"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("Validation Failed"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.validationErrors").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.validationErrors.file").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.validationErrors.file.errors",Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.validationErrors.creationDate").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.validationErrors.creationDate.errors",Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.validationErrors.title").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.validationErrors.title.errors",Matchers.hasSize(1)));

    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(get("/api/files")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalResults").value(40))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfItemsPerPage").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfPages").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("File Title 40"));

        mockMvc.perform(get("/api/files").param("page", "1")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalResults").value(40))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfItemsPerPage").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfPages").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("File Title 20"));


        mockMvc.perform(get("/api/files").param("page", "2")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalResults").value(40))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfItemsPerPage").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfPages").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.empty()));

    }

    @Test
    public void delete() throws Exception {
        MediaFile mediaFile = mediaFileList.get(0);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/files/" + mediaFile.getId())).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.message").value("Media File deleted successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(mediaFile.getId()));

    }

    @Test
    public void delete_whenDoesNotExist_statusNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/files/" + Long.MAX_VALUE)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error.message").value("Error resource not found"));

    }
}
