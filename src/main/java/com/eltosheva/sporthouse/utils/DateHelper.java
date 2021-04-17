package com.eltosheva.sporthouse.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    public static String dateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, E"));
    }
}
