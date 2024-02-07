package ru.courses.innotech.api.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDateUtil {

  public static final DateTimeFormatter DATE_TIME_FORMATTER_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static LocalDate getLocalDate(String value, DateTimeFormatter formatter) {
    if (value == null || value.isEmpty()) {
      return null;
    }
    return LocalDate.parse(value, formatter);
  }

}
