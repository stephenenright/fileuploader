package com.github.stephenenright.fileuploader.storage.file.utils;

public abstract class AssertUtils {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void strNotNullAndNotEmpty(String str, String message) {
        if (StringUtils.isNullOrEmpty(str)) {
            throw new IllegalArgumentException(message);
        }
    }


}
