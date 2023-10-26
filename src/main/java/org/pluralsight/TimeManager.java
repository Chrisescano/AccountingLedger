package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeManager {

    public static LocalDateTime[] monthRangeOf(LocalDateTime date) {
        LocalDate beginningOfMonth = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
        return new LocalDateTime[] {
                beginningOfMonth.atTime(LocalTime.MIN),
                beginningOfMonth.plusMonths(1).minusDays(1).atTime(LocalTime.MAX)
        };
    }

    public static LocalDateTime[] yearRangeOf(LocalDateTime date) {
        LocalDate beginningOfYear = LocalDate.of(date.getYear(), 1, 1);
        return new LocalDateTime[] {
                beginningOfYear.atTime(LocalTime.MIN),
                beginningOfYear.plusYears(1).minusDays(1).atTime(LocalTime.MAX)
        };
    }
}
