package org.pluralsight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record LedgerPost(LocalDateTime timeStamp, String description, String vendor, double amount) {

    public String toTableFormat(String format) {
        return String.format(
                format, getDate(), getTime(), description(), vendor(), amount()
        );
    }

    /*-----Getters-----*/

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return timeStamp.format(formatter);
    }

    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timeStamp.format(formatter);
    }

    /*-----Overrides-----*/

    @Override
    public String toString() {
        return getDate() + "|" +
                getTime() + "|" +
                description() + "|" +
                vendor() + "|" +
                amount();
    }
}
