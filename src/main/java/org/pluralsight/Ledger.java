package org.pluralsight;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Ledger {
    private final ArrayList<Transaction> ledgerMasterCopy;
    private final String DEFAULT_FORMAT = "| %10s @ %8s | %-30.30s | %-15.15s | $%10.2f |";
    //private final String tableHeader = "| %-10s @ %-8s | %-30s | %-15s | %-11s |\n";
    private final String tableDivider = "+" +
            "-".repeat(23) + "+" +
            "-".repeat(32) + "+" +
            "-".repeat(17) + "+" +
            "-".repeat(13) + "+";

    /*
    To shorting the ledger class might have to move sorting methods to new class.
    technically speaking a ledger does not sort itself, something else would. A ledger
    is only supposed to be able to hold information of entries, display itself, and change
    its formatting.
     */

    public Ledger() {
        ledgerMasterCopy = new ArrayList<>();
    }

    public static void main(String[] args) {
        Ledger ledger = new Ledger();
        ledger.sortByPreviousYear();
    }

    /*-----Methods-----*/

    public void postToLedger(LocalDateTime timeStamp, String description, String vendor, double amount) {
        ledgerMasterCopy.add(new Transaction(timeStamp, description, vendor, amount));
    }



    private ArrayList<Transaction> deepCopy() {
        return new ArrayList<>(ledgerMasterCopy);
    }

    /*-----Display Methods-----*/

    public void displayLedgerAsTable() {
        displayLedgerAsTable(DEFAULT_FORMAT, ledgerMasterCopy);
    }

    public void displayLedgerAsTable(ArrayList<Transaction> ledger) {
        displayLedgerAsTable(DEFAULT_FORMAT, ledger);
    }

    public void displayLedgerAsTable(String format, ArrayList<Transaction> ledger) {
        displayTableHeader();
        for(Transaction transaction : ledger)
            displayTableEntry(transaction.toTableFormat(format));
    }

    private void displayTableHeader() {
        System.out.println(tableDivider);
        System.out.printf(
                "| %-10s @ %-8s | %-30s | %-15s | %-11s |\n",
                "Date", "Time", "Description", "Vendor", "Price");
        System.out.println(tableDivider);
    }

    private void displayTableEntry(String tableEntry) {
        System.out.println(tableEntry);
        System.out.println(tableDivider);
    }

    /*-----Report Sorting Methods-----*/

    public void displayPaymentsOnly() {
        ArrayList<Transaction> tmpLedger = deepCopy();
        tmpLedger.removeIf(transaction -> transaction.amount() > 0);
        displayLedgerAsTable(tmpLedger);
    }

    public void displayDepositsOnly() {
        ArrayList<Transaction> tmpLedger = deepCopy();
        tmpLedger.removeIf(transaction -> transaction.amount() < 0);
        displayLedgerAsTable(tmpLedger);
    }

    public void sortFromMonthToDate() {
        ArrayList<Transaction> tmpLedger = deepCopy();
        LocalDateTime beginningOfMonth = getStartOfMonth();

        tmpLedger.removeIf(transaction -> transaction.timeStamp().isBefore(beginningOfMonth));
        displayLedgerAsTable(tmpLedger);
    }

    public void sortByPreviousMonth() {
        ArrayList<Transaction> tmpLedger = deepCopy();
        LocalDateTime beginningOfLastMonth = getStartOfMonth().minusMonths(1);
        LocalDateTime endOfLastMonth = getStartOfMonth().minusSeconds(1);

        tmpLedger.removeIf(transaction -> transaction.timeStamp().isBefore(beginningOfLastMonth) ||
                transaction.timeStamp().isAfter(endOfLastMonth));
        displayLedgerAsTable(tmpLedger);
    }

    public void sortFromYearToDate() {
        ArrayList<Transaction> tmpLedger = deepCopy();
        LocalDateTime beginningOfYear = getStartOfYear();

        tmpLedger.removeIf(transaction -> transaction.timeStamp().isBefore(beginningOfYear));
        displayLedgerAsTable(tmpLedger);
    }

    public void sortByPreviousYear() {
        ArrayList<Transaction> tmpLedger = deepCopy();
        LocalDateTime beginningOfLastYear = getStartOfYear().minusYears(1);
        LocalDateTime endOfLastYear = getStartOfYear().minusSeconds(1);

        tmpLedger.removeIf(transaction -> transaction.timeStamp().isBefore(beginningOfLastYear) ||
                transaction.timeStamp().isAfter(endOfLastYear));
        displayLedgerAsTable(tmpLedger);
    }

    public void sortByVendor(String vendor) {
        ArrayList<Transaction> tmpLedger = deepCopy();
        tmpLedger.removeIf(transaction -> !transaction.vendor().equalsIgnoreCase(vendor));
        displayLedgerAsTable(tmpLedger);
    }

    /*-----Helper Functions-----*/

    private LocalDateTime getStartOfMonth() {
        return LocalDateTime.of(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                1,
                0,
                0,
                0
        );
    }

    private LocalDateTime getStartOfYear() {
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
