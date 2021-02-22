package com.github.stephenenright.fileuploader.common.validation.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public abstract class ValidationUtils {

    private static final DateTimeFormatter ISO_DATE_TIME_FORMATTER;

    static {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        ISO_DATE_TIME_FORMATTER = builder
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_DATE_TIME)
                .toFormatter();
    }


    //TODO maybe use regex
    public static boolean isISODateTime(String value) {
        try {
            ISO_DATE_TIME_FORMATTER.parse(value);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
