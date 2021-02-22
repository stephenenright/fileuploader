package com.github.stephenenright.fileuploader.common.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;

public abstract class ZonedDateTimeUtils {

    private static final DateTimeFormatter ISO_DATE_TIME_FORMATTER;

    private static final String TIMEZONE_UTC = "UTC";


    static {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        ISO_DATE_TIME_FORMATTER = builder
                .parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_DATE_TIME)
                .toFormatter();
    }

    public static Long toMillis(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(ZoneId.of(TIMEZONE_UTC)).toInstant().toEpochMilli();
    }


    public static Optional<ZonedDateTime> parseISODateTimeAsUtc(String value) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.parse(value, ISO_DATE_TIME_FORMATTER);

            if (!dateTime.getZone().getId().equals(TIMEZONE_UTC)) {
                dateTime = dateTime.withZoneSameInstant(ZoneId.of(TIMEZONE_UTC));
            }

            return Optional.of(dateTime);
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    public static ZonedDateTime getNowUtc() {
        return ZonedDateTime.now(ZoneId.of(TIMEZONE_UTC));
    }


}









