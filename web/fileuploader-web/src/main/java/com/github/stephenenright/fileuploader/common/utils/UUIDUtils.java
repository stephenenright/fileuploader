package com.github.stephenenright.fileuploader.common.utils;

import java.util.UUID;

public abstract class UUIDUtils {

    public static String createUUIDWithoutSpaces() {
        return UUID.randomUUID().toString().replaceAll(" ", "");
    }
}
