package com.github.stephenenright.fileuploader;

import com.github.stephenenright.fileuploader.api.common.response.ApiError;
import com.github.stephenenright.fileuploader.common.messages.ErrorMessages;
import com.github.stephenenright.fileuploader.configuration.settings.MultipartUploadSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    private final MultipartUploadSettings multipartSettings;

    public RestExceptionHandler(MultipartUploadSettings multipartSettings) {
        this.multipartSettings = multipartSettings;
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleAll(Throwable t) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("error", ApiError.builder().message(ErrorMessages.ERROR_UNEXPECTED).build());

        if (log.isErrorEnabled()) {
            log.error(t.getMessage(), t);
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(responseMap);

    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleUploadSizeExceededException(MaxUploadSizeExceededException musee) {

        if (log.isErrorEnabled()) {
            log.error(musee.getMessage(), musee);
        }


        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("error", ApiError.builder().message(String.format("File upload not allowed. File size exceeds " +
                "the maximum allowed file upload size of %s", multipartSettings.getMaxFileSize())).build());
        return ResponseEntity.badRequest().body(responseMap);
    }





}
