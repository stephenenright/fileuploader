package com.github.stephenenright.fileuploader.common.validation;

import java.util.List;

public class ValidationErrorStandard extends ValidationError {

    private List<String> errors;

    public ValidationErrorStandard(String label, String error) {
        this(label,List.of(error));
    }


    public ValidationErrorStandard(String label, List<String> errors) {
        super(label);
        this.errors = errors;
    }



    @Override
    public List<String> getErrors() {
        return errors;
    }


}
