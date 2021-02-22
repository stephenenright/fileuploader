package com.github.stephenenright.fileuploader.storage.file.utils;

public abstract class StringUtils {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().equals("");
    }
}
