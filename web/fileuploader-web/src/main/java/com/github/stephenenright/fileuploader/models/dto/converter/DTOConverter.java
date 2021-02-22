package com.github.stephenenright.fileuploader.models.dto.converter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public interface DTOConverter<M, D> {

    default M fromDTOToModel(D dto, Object... additionalParams) {
        return null;
    }

    default D fromModelToDTO(M model, Object... additionalParams) {
        return null;
    }

    default <T, R> R nullSafe(T value, Function<T, R> function) {
        return nonNull(value) ? function.apply(value) : null;
    }

    default List<M> fromDTOToModel(Collection<D> dtoCollection, Object... additionalParams) {
        if (isNull(dtoCollection))
            return Collections.emptyList();

        return dtoCollection.stream()
                .filter(Objects::nonNull)
                .map(m -> fromDTOToModel(m, additionalParams))
                .collect(Collectors.toList());
    }

    default List<D> fromModelToDTO(Collection<M> modelCollection, Object... additionalParams) {
        if (isNull(modelCollection))
            return Collections.emptyList();

        return modelCollection.stream()
                .filter(Objects::nonNull)
                .map(m -> fromModelToDTO(m, additionalParams))
                .collect(Collectors.toList());
    }

}
