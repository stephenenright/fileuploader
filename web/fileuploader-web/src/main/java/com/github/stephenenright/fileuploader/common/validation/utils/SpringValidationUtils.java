package com.github.stephenenright.fileuploader.common.validation.utils;

import com.github.stephenenright.fileuploader.common.validation.ValidationError;
import com.github.stephenenright.fileuploader.common.validation.ValidationErrorIndexed;
import com.github.stephenenright.fileuploader.common.validation.ValidationErrorStandard;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SpringValidationUtils {

    private static final String PATTERN = "[a-zA-Z]+\\[\\d+]";
    private static final String FULL_PATTERN = "([a-zA-Z]+)\\[(\\d+)]\\.([a-zA-Z]+)";
    private static final Pattern pattern = Pattern.compile(FULL_PATTERN);

    public static Map<String, ValidationError> buildValidationErrors(BindingResult bindingResult) {
        return buildValidationErrors(bindingResult.getFieldErrors());
    }

    @SuppressWarnings("unchecked")
    private static Map<String, ValidationError> buildValidationErrors(List<FieldError> fieldErrors) {
        Map<String, ValidationError> validationErrors = new HashMap<>();
        if (fieldErrors == null || fieldErrors.isEmpty()) {
            return validationErrors;
        }
        fieldErrors.forEach(fieldError -> {
            Matcher matcher = pattern.matcher(fieldError.getField());
            if (matcher.find()) {
                String label = matcher.group(1);
                String index = matcher.group(2);
                String key = matcher.group(3);


                ValidationError validationIndexedListError = validationErrors.computeIfAbsent(label,
                        k -> new ValidationErrorIndexed(label, new HashMap<>()));
                Map<String, Map<String, List<String>>> errors = (Map<String, Map<String, List<String>>>) validationIndexedListError
                        .getErrors();
                errors.computeIfAbsent(index, k -> new HashMap<>()).computeIfAbsent(key, k -> new ArrayList<>())
                        .add(fieldError.getDefaultMessage());
            } else if (!fieldError.getField().matches(PATTERN)) {
                ValidationError standardValidationError = validationErrors.computeIfAbsent(fieldError.getField(),
                        k -> new ValidationErrorStandard(fieldError.getField(), new ArrayList<>()));
                List<String> errors = (List<String>) standardValidationError.getErrors();
                errors.add(fieldError.getDefaultMessage());
            }
        });

        return validationErrors;
    }


}
