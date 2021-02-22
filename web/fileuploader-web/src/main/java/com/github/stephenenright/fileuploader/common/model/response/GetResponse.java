package com.github.stephenenright.fileuploader.common.model.response;

import com.github.stephenenright.fileuploader.common.model.response.impl.GetResponseImpl;
import com.github.stephenenright.fileuploader.common.validation.ValidationError;

import java.util.Map;
import java.util.Optional;

public interface GetResponse<RT> {

    boolean isOk();

    boolean isNotFoundError();

    Optional<Map<String, ValidationError>> getValidationErrors();

    Optional<String> getErrorMessage();

    Optional<String> getErrorCode();

    Optional<RT> getResult();


    static <RT> GetResponse<RT> createSuccessResponse(RT result) {
        return new GetResponseImpl<>(false, null, null, result, null);
    }

    static <T> GetResponse<T> createNotFoundResponse() {
        return new GetResponseImpl<>(true, null);
    }


}
