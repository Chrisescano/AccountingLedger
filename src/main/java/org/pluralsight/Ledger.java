package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Ledger {
    private ArrayList<LedgerPost> ledgerMasterCopy;
    private String DEFAULT_FORMAT = "| %10s @ %8s | %-30.30s | %-15.15s | $%10.2f |";
    private String tableHeader = "| %-10s @ %-8s | %-30s | %-15s | %-11s |\n";
    private String tableDivider = "+" +
            "-".repeat(23) + "+" +
            "-".repeat(32) + "+" +
            "-".repeat(17) + "+" +
            "-".repeat(13) + "+";

    public Ledger() {
        ledgerMasterCopy = new ArrayList<>();
    }

    public static void main(String[] args) {
        Ledger ledger = new Ledger();
        ledger.sortByPreviousYear();
    }

    /*-----Methods-----*/

    public void postToLedger(LocalDateTime timeStamp, String description, String vendor, double amount) {
        ledgerMasterCopy.add(new LedgerPost(timeStamp, description, vendor, amount));
    }

    private ArrayList<LedgerPost> deepCopy() {
        return new ArrayList<>(ledgerMasterCopy);
    }

    /*-----Display Methods-----*/

    public void displayLedgerAsTable() {
        displayLedgerAsTable(DEFAULT_FORMAT, ledgerMasterCopy);
    }

    public void displayLedgerAsTable(ArrayList<LedgerPost> ledger) {
        displayLedgerAsTable(DEFAULT_FORMAT, ledger);
    }

    public void displayLedgerAsTable(String format, ArrayList<LedgerPost> ledger) {
        displayTableHeader();
        for(LedgerPost ledgerPost : ledger)
            displayTableEntry(ledgerPost.toTableFormat(format));
    }

    private void displayTableHeader() {
        System.out.println(tableDivider);
        System.out.printf(tableHeader, "Date", "Time", "Description", "Vendor", "Price");
        System.out.println(tableDivider);
    }

    private void displayTableEntry(String tableEntry) {
        System.out.println(tableEntry);
        System.out.println(tableDivider);
    }

    /*-----Sorting Methods-----*/

    public void displayPaymentsOnly() {
        ArrayList<LedgerPost> tmpLedger = deepCopy();
        tmpLedger.removeIf(ledgerPost -> ledgerPost.getAmount() > 0);
        displayLedgerAsTable(DEFAULT_FORMAT, tmpLedger);
    }

    public void displayDepositsOnly() {
        ArrayList<LedgerPost> tmpLedger = deepCopy();
        tmpLedger.removeIf(ledgerPost -> ledgerPost.getAmount() < 0);
        displayLedgerAsTable(DEFAULT_FORMAT, tmpLedger);
    }

    public void sortFromMonthToDate() {
        ArrayList<LedgerPost> tmpLedger = deepCopy();
        LocalDateTime beginningOfMonth = getStartOfMonth();

        tmpLedger.removeIf(ledgerPost -> ledgerPost.getTimeStamp().isBefore(beginningOfMonth));
        displayLedgerAsTable(DEFAULT_FORMAT, tmpLedger);
    }

    public void sortByPreviousMonth() {
        ArrayList<LedgerPost> tmpLedger = deepCopy();
        LocalDateTime beginningOfLastMonth = getStartOfMonth().minusMonths(1);
        LocalDateTime endOfLastMonth = getStartOfMonth().minusSeconds(1);

        tmpLedger.removeIf(ledgerPost -> ledgerPost.getTimeStamp().isBefore(beginningOfLastMonth) ||
                ledgerPost.getTimeStamp().isAfter(endOfLastMonth));
        displayLedgerAsTable(DEFAULT_FORMAT, tmpLedger);
    }

    public void sortFromYearToDate() {
        ArrayList<LedgerPost> tmpLedger = deepCopy();
        LocalDateTime beginningOfYear = getStartOfYear();

        tmpLedger.removeIf(ledgerPost -> ledgerPost.getTimeStamp().isBefore(beginningOfYear));
        displayLedgerAsTable(DEFAULT_FORMAT, tmpLedger);
    }

    public void sortByPreviousYear() {
        ArrayList<LedgerPost> tmpLedger = deepCopy();
        LocalDateTime beginningOfLastYear = getStartOfYear().minusYears(1);
        LocalDateTime endOfLastYear = getStartOfYear().minusSeconds(1);

        tmpLedger.removeIf(ledgerPost -> ledgerPost.getTimeStamp().isBefore(beginningOfLastYear) ||
                ledgerPost.getTimeStamp().isAfter(endOfLastYear));
        displayLedgerAsTable(DEFAULT_FORMAT, tmpLedger);
    }

    public void sortByVendor(String vendor) {
        ArrayList<LedgerPost> tmpLedger = deepCopy();
        tmpLedger.removeIf(ledgerPost -> !ledgerPost.getVendor().equalsIgnoreCase(vendor));
        displayLedgerAsTable(DEFAULT_FORMAT, tmpLedger);
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
