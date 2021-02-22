package com.github.stephenenright.fileuploader.common.messages;

import com.github.stephenenright.fileuploader.common.utils.ClassUtils;

//TODO POSSIBLY I18n
public abstract class SuccessMessages {

    private static final String SUCCESS_CREATED_FORMAT = "%s created successfully";
    private static final String SUCCESS_DELETED_FORMAT = "%s deleted successfully";


    public static String created(Class<?> modelClass) {
        return String.format(SUCCESS_CREATED_FORMAT, ClassUtils.getClassNameWords(modelClass));
    }

    public static String deleted(Class<?> modelClass) {
        return String.format(SUCCESS_DELETED_FORMAT, ClassUtils.getClassNameWords(modelClass));
    }

}
