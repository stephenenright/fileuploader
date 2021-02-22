package com.github.stephenenright.fileuploader.models.dto;

import com.github.stephenenright.fileuploader.models.dto.converter.DTOConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResultDTO<R> {

    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
    private List<R> content;

    public static <T> PageResultDTO<T> createFrom(Page<T> page) {
        return PageResultDTO.<T>builder()
                .number(page.getNumber())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .content(page.getContent())
                .build();
    }

    public static <S, T> PageResultDTO<T> createFrom(Page<S> page, DTOConverter<? super S, ? extends T> converter) {
        return createFrom(page.map(converter::fromModelToDTO));
    }


}