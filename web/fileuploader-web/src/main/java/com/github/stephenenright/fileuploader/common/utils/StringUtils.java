package com.github.stephenenright.fileuploader.common.utils;

public abstract class StringUtils {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    public static boolean isNotNullAndNotEmpty(String str) {
        return !isNullOrEmpty(str);
    }
}
