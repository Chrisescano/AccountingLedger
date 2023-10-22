package org.pluralsight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LedgerPost {
    private LocalDateTime timeStamp;
    private String description;
    private String vendor;
    private double amount;

    public LedgerPost(LocalDateTime timeStamp, String description, String vendor, double amount) {
        this.timeStamp = timeStamp;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
//    public LedgerPost(int year, int month, int day, int hour, int minute, int second,
//                      String description, String vendor, double amount) {
//        this(LocalDateTime.of(year, month, day, hour, minute, second), description, vendor, amount);
//    }

    public String toTableFormat(String format) {
        return String.format(
                format, getDate(), getTime(), getDescription(), getVendor(), getAmount()
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

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    /*-----Overrides-----*/

    @Override
    public String toString() {
        return getDate() + "|" +
               getTime() + "|" +
               getDescription() + "|" +
               getVendor() + "|" +
               getAmount();
    }
}
