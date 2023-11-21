package com.sis.retrospective.controller.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatting {

    public static final String DATE_PATTERN = "yyyy/MM/dd";

    public static LocalDate parse(String dateStr) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
}
