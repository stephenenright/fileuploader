package com.github.stephenenright.fileuploader.api.controller;

import com.github.stephenenright.fileuploader.api.common.functions.ApiFunctions.CreateApiCall;
import com.github.stephenenright.fileuploader.api.common.functions.ApiFunctions.DeleteApiApiCall;
import com.github.stephenenright.fileuploader.api.common.functions.ApiFunctions.ListPagedApiCall;
import com.github.stephenenright.fileuploader.api.common.response.ApiResponse;
import com.github.stephenenright.fileuploader.api.common.response.utils.ApiResponseUtils;
import com.github.stephenenright.fileuploader.common.messages.ErrorMessages;
import com.github.stephenenright.fileuploader.common.model.request.PagingRequest;
import com.github.stephenenright.fileuploader.common.model.response.CreateResponse;
import com.github.stephenenright.fileuploader.common.model.response.DeleteResponse;
import com.github.stephenenright.fileuploader.common.model.response.GetResponse;
import com.github.stephenenright.fileuploader.models.dto.PageResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Slf4j
public abstract class BaseController<T> {

    private Class<T> modelClass;

    public BaseController() {
        modelClass = getModelClass();
    }


    //TODO MAKE THIS CONFIGURABLE
    private static final int MAX_RESULTS_PER_PAGE = 100;

    //TODO MAKE THIS CONFIGURABLE
    private static final int MIN_RESULTS_PER_PAGE = 10;


    public <RT> ResponseEntity<ApiResponse<RT>> apiListPaged(ListPagedApiCall<RT> listCall, PagingRequest pagingRequest) {
        try {
            GetResponse<PageResultDTO<RT>> getResponse = listCall.execute(PagingRequest.sanitized(pagingRequest, MAX_RESULTS_PER_PAGE, MIN_RESULTS_PER_PAGE));

            if (getResponse.isOk()) {
                return ResponseEntity.ok(ApiResponseUtils.pagedResponse(getResponse.getResult().orElse(null)));
            }

            if (getResponse.getValidationErrors().isPresent()) {
                return ResponseEntity.badRequest().body(
                        ApiResponseUtils.createValidationFailedErrorResponse(
                                getResponse.getErrorMessage().orElse(null), getResponse.getErrorCode().orElse(null),
                                getResponse.getValidationErrors().orElse(null)));
            } else if (getResponse.isNotFoundError()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ApiResponseUtils.createNotFoundErrorResponse(
                                getResponse.getErrorMessage().orElse(null), getResponse.getErrorCode().orElse(null)));
            }


            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseUtils.createErrorResponse(
                            getResponse.getErrorMessage().orElse(null), getResponse.getErrorCode().orElse(null),
                            getResponse.getValidationErrors().orElse(null)));

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Getting paged list failed", e);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseUtils.createErrorResponse(ErrorMessages.ERROR_UNEXPECTED, null, null));
        }
    }


    public <RT> ResponseEntity<ApiResponse<RT>> apiCreate(CreateApiCall<RT> createCall, BindingResult bindingResult) {

        try {

            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(
                        ApiResponseUtils.createValidationFailedResponse(bindingResult));
            }

            CreateResponse<RT> createResponse = createCall.execute();

            if (createResponse.isCreated()) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponseUtils.
                                creationSuccessResponse(createResponse, modelClass));
            }

            if (createResponse.getValidationErrors().isPresent()) {
                return ResponseEntity.badRequest().body(
                        ApiResponseUtils.createValidationFailedErrorResponse(
                                createResponse.getErrorMessage().orElse(null), createResponse.getErrorCode().orElse(null),
                                createResponse.getValidationErrors().orElse(null))
                );

            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseUtils.createErrorResponse(
                            createResponse.getErrorMessage().orElse(null), createResponse.getErrorCode().orElse(null),
                            createResponse.getValidationErrors().orElse(null))
            );

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Creation failed", e);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseUtils.createErrorResponse(ErrorMessages.ERROR_CREATION, null, null));
        }
    }


    public <RT> ResponseEntity<ApiResponse<RT>> apiDelete(DeleteApiApiCall<RT> deleteCall) {
        try {

            DeleteResponse<RT> deleteResponse = deleteCall.execute();

            if (deleteResponse.isDeleted()) {
                return ResponseEntity.ok(
                        ApiResponseUtils.deleteSuccessResponse(deleteResponse, modelClass));
            } else if (deleteResponse.isNotFound()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ApiResponseUtils.createNotFoundErrorResponse(
                                deleteResponse.getErrorMessage().orElse(null), deleteResponse.getErrorCode().orElse(null)));
            }


            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseUtils.createErrorResponse(
                            deleteResponse.getErrorMessage().orElse(null), deleteResponse.getErrorCode().orElse(null),
                            null));

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Deletion failed", e);
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseUtils.createErrorResponse(ErrorMessages.ERROR_DELETION, null, null));
        }
    }

    @SuppressWarnings("unchecked")
    private Class<T> getModelClass() {
        if (this.modelClass == null) {
            this.modelClass = this.getOverriddenModelClass();
            if (this.modelClass == null) {
                ParameterizedType thisType = (ParameterizedType) this.getClass().getGenericSuperclass();
                Type[] args = thisType.getActualTypeArguments();
                this.modelClass = (Class<T>) thisType.getActualTypeArguments()[0];
            }
        }

        return this.modelClass;
    }

    protected Class<T> getOverriddenModelClass() {
        return null;
    }


}
