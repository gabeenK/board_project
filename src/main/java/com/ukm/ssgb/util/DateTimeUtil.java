package com.ukm.ssgb.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtil {

    public static LocalDateTime startDateOfTomorrow() {
        return LocalDate.now().plusDays(1).atStartOfDay();
    }

    public static LocalDateTime startDateOfLastMonth() {
        return LocalDate.now().minusMonths(1).atStartOfDay();
    }
}
