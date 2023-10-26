package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class LedgerStats {

    private static ArrayList<Transaction> ledger;

    public static void main(String[] args) {
        yearlySummary(2023);
    }

    public LedgerStats(ArrayList<Transaction> ledger) {
        this.ledger = ledger;
    }

    public static void totalBalance() {
        double sum = 0;
        for(Transaction transaction : ledger) { sum += transaction.amount(); }
    }

    //we can loop for all months of the year, and ask the user for the year and month
    public static void monthlySummary(int year, int month) {
        LocalDate[] dateRange = TimeManager.getStartAndEndOfMonth(year, month);


    }

    public static void yearlySummary(int year) {
        LocalDate[] yearRange = TimeManager.getStartAndEndOfYear(year);

    }
}
