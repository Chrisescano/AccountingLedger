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
        StringBuilder sb = new StringBuilder();
        sb.append(getDate() + "|");
        sb.append(getTime() + "|");
        sb.append(getDescription() + "|");
        sb.append(getVendor() + "|");
        sb.append(getAmount());
        return sb.toString();
    }
}
