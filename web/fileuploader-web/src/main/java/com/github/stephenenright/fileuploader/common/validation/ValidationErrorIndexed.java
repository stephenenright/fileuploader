package com.github.stephenenright.fileuploader.common.validation;

import java.util.List;
import java.util.Map;

public class ValidationErrorIndexed extends ValidationError {

    private Map<String, Map<String, List<String>>> errors;

    public ValidationErrorIndexed(String label, Map<String, Map<String, List<String>>> errors) {
        super(label);
        this.errors = errors;
    }

    @Override
    public Map<String, Map<String, List<String>>> getErrors() {
        return errors;
    }
}
