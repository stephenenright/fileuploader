package com.github.stephenenright.fileuploader.common.utils;

public abstract class FileNameUtils {

    public static String appendExtension(String name, String extension) {
        if (StringUtils.isNullOrEmpty(extension)) {
            return name;
        }

        StringBuilder builder = new StringBuilder(name);
        builder.append(".");
        builder.append(extension);

        return builder.toString();
    }


}
