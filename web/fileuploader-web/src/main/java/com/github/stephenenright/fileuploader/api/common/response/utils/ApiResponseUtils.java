package com.github.stephenenright.fileuploader.api.common.response.utils;

import com.github.stephenenright.fileuploader.api.common.response.*;
import com.github.stephenenright.fileuploader.common.messages.ErrorMessages;
import com.github.stephenenright.fileuploader.common.messages.SuccessMessages;
import com.github.stephenenright.fileuploader.common.model.response.GetResponse;
import com.github.stephenenright.fileuploader.common.utils.StringUtils;
import com.github.stephenenright.fileuploader.common.validation.ValidationError;
import com.github.stephenenright.fileuploader.common.validation.utils.SpringValidationUtils;
import com.github.stephenenright.fileuploader.common.model.request.PagingRequest;
import com.github.stephenenright.fileuploader.common.model.response.CreateResponse;
import com.github.stephenenright.fileuploader.common.model.response.DeleteResponse;
import com.github.stephenenright.fileuploader.models.dto.PageResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Map;

public abstract class ApiResponseUtils {

    public static <RT> ApiResponse<RT> createValidationFailedResponse(BindingResult bindingResult) {
        return ApiErrorResponse.<RT>builder()
                .error(ApiError.builder()
                        .message(ErrorMessages.ERROR_VALIDATION_FAILED)
                        .validationErrors(SpringValidationUtils.buildValidationErrors(bindingResult))
                        .build()
                ).build();
    }

    public static <RT> ApiResponse<RT> createValidationFailedErrorResponse(
            String message, String errorCode,
            Map<String, ValidationError> validationErrors) {
        return ApiErrorResponse.<RT>builder()
                .error(ApiError.builder()
                        .message(StringUtils.isNullOrEmpty(message) ? ErrorMessages.ERROR_VALIDATION_FAILED : message)
                        .errorCode(errorCode)
                        .validationErrors(validationErrors)
                        .build()
                ).build();
    }

    public static <RT> ApiResponse<RT> createNotFoundErrorResponse(
            String message, String errorCode) {
        return ApiErrorResponse.<RT>builder()
                .error(ApiError.builder()
                        .message(StringUtils.isNullOrEmpty(message) ? ErrorMessages.ERROR_NOT_FOUND: message)
                        .errorCode(errorCode)
                        .validationErrors(null)
                        .build()
                ).build();
    }


    public static <RT> ApiResponse<RT> createErrorResponse(
            String message, String errorCode,
            Map<String, ValidationError> validationErrors) {
        return ApiErrorResponse.<RT>builder()
                .error(ApiError.builder()
                        .message(StringUtils.isNullOrEmpty(message) ? ErrorMessages.ERROR_UNEXPECTED : message)
                        .errorCode(errorCode)
                        .validationErrors(validationErrors)
                        .build()
                ).build();
    }

    public static <RT> ApiResponse<RT> createErrorResponse(
            GetResponse<RT> response) {
        return ApiErrorResponse.<RT>builder()
                .error(ApiError.builder()
                        .message(response.getErrorMessage().orElse(ErrorMessages.ERROR_UNEXPECTED))
                        .errorCode(response.getErrorCode().orElse(null))
                        .validationErrors(response.getValidationErrors().orElse(null))
                        .build()
                ).build();
    }


    public static <RT> ApiResponse<RT> creationSuccessResponse(CreateResponse<RT> createResponse, Class<?> createdCls) {
        return ApiSuccessResponse.<RT>builder()
                .success(ApiSuccess.builder().message(createResponse.getMessage()
                        .orElse(SuccessMessages.created(createdCls)))
                        .build())
                .data(createResponse.getResult().orElse(null))
                .build();
    }

    public static <RT> ApiResponse<RT> deleteSuccessResponse(DeleteResponse<RT> deleteResponse, Class<?> deletedCls) {
        return ApiSuccessResponse.<RT>builder()
                .success(ApiSuccess.builder().message(deleteResponse.getMessage().orElse(SuccessMessages.deleted(deletedCls)))
                        .build())
                .data(deleteResponse.getResult().orElse(null))
                .build();

    }

    public static <RT> ApiResponse<RT> pagedResponse(PageResultDTO<RT> pagedResult) {
        return ApiPagedResponse.<RT>builder()
                .data(pagedResult != null ? pagedResult.getContent() : new ArrayList<>())
                .numberOfItemsPerPage(pagedResult != null ? pagedResult.getSize() : 0)
                .numberOfPages(pagedResult != null ? pagedResult.getTotalPages() : 0)
                .pageNumber(pagedResult != null ? pagedResult.getNumber() : 0)
                .totalResults(pagedResult != null ? pagedResult.getTotalElements() : 0)
                .build();
    }
}
