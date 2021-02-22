package com.github.stephenenright.fileuploader.common.validation;

public abstract class ValidationError {

    private String label;

    public ValidationError(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public abstract Object getErrors();

}

