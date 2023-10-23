package org.pluralsight;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sorter {

    public static ArrayList<Transaction> depositsOnly(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> sortedLedger = new ArrayList<>(ledger);
        sortedLedger.removeIf(transaction -> transaction.amount() < 0);
        return sortedLedger;
    }

    public static ArrayList<Transaction> paymentsOnly(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> sortedLedger = new ArrayList<>(ledger);
        sortedLedger.removeIf(transaction -> transaction.amount() > 0);
        return sortedLedger;
    }

    public static ArrayList<Transaction> fromMonthToDate(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> sortedLedger = new ArrayList<>(ledger);
        LocalDateTime beginningOfMonth = getStartOfMonth();

        sortedLedger.removeIf(transaction -> transaction.timeStamp().isBefore(beginningOfMonth));
        return sortedLedger;
    }

    public static ArrayList<Transaction> byPreviousMonth(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> sortedLedger = new ArrayList<>(ledger);
        LocalDateTime beginningOfLastMonth = getStartOfMonth().minusMonths(1);
        LocalDateTime endOfLastMonth = getStartOfMonth().minusSeconds(1);

        sortedLedger.removeIf(transaction -> transaction.timeStamp().isBefore(beginningOfLastMonth) ||
                transaction.timeStamp().isAfter(endOfLastMonth));
        return sortedLedger;
    }

    public static ArrayList<Transaction> fromYearToDate(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> sortedLedger = new ArrayList<>(ledger);
        LocalDateTime beginningOfYear = getStartOfYear();

        sortedLedger.removeIf(transaction -> transaction.timeStamp().isBefore(beginningOfYear));
        return sortedLedger;
    }

    public static ArrayList<Transaction> byPreviousYear(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> sortedLedger = new ArrayList<>(ledger);
        LocalDateTime beginningOfLastYear = getStartOfYear().minusYears(1);
        LocalDateTime endOfLastYear = getStartOfYear().minusSeconds(1);

        sortedLedger.removeIf(transaction -> transaction.timeStamp().isBefore(beginningOfLastYear) ||
                transaction.timeStamp().isAfter(endOfLastYear));
        return sortedLedger;
    }

    public static ArrayList<Transaction> byVendor(String vendor, ArrayList<Transaction> ledger) {
        ArrayList<Transaction> sortedLedger = new ArrayList<>(ledger);
        sortedLedger.removeIf(transaction -> !transaction.vendor().equalsIgnoreCase(vendor));
        return sortedLedger;
    }

    /*-----Helper Functions-----*/

    private static LocalDateTime getStartOfMonth() {
        return LocalDateTime.of(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                1,
                0,
                0,
                0
        );
    }

    private static LocalDateTime getStartOfYear() {
        return LocalDateTime.of(
                LocalDateTime.now().getYear(),
                1,
                1,
                0,
                0,
                0
        );
    }
}
