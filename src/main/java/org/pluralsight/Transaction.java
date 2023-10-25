package org.pluralsight;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Transaction(LocalDateTime timeStamp, String description, String vendor, double amount) {

    public String toCSVFormat() {
        return getDate() + "|" + getTime() + "|" + description() + "|" + vendor() + "|" + amount() + "\n";
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

    public String getAmount() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(amount);
    }

    /*-----Overrides-----*/

    @Override
    public String toString() {
        return "Transaction{timeStamp: " + timeStamp +
                ", description: " + description +
                ", vendor: " + vendor +
                ", amount: " + amount + "}";
    }
}
