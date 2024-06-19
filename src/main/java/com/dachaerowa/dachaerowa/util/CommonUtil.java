package com.dachaerowa.dachaerowa.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CommonUtil {
    public static LocalDateTime convertStringToLocalDateTime(String dateTimeString) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
