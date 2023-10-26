package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeManager {

    public static LocalDate[] getStartAndEndOfMonth(int year, int month) {
        LocalDate beginningOfMonth = LocalDate.of(year, month, 1);
        return new LocalDate[] {
                beginningOfMonth, beginningOfMonth.plusMonths(1).minusDays(1)
        };
    }

    public static LocalDate[] getStartAndEndOfYear(int year) {
        LocalDate beginningOfYear = LocalDate.of(year, 1, 1);
        return new LocalDate[] {
                beginningOfYear, beginningOfYear.plusYears(1).minusDays(1)
        };
    }

    public static LocalDateTime toLocalDateTime(LocalDate date, boolean isMinTime) {
        return isMinTime ? date.atTime(LocalTime.MIN) : date.atTime(LocalTime.MAX);
    }
}
