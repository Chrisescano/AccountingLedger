package org.pluralsight;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Ledger {
    private final ArrayList<Transaction> masterCopy;
    private final String DEFAULT_FORMAT = "| %10s @ %8s | %-30.30s | %-15.15s | $%10.2f |";
    private final String tableDivider = "+" +
            "-".repeat(23) + "+" +
            "-".repeat(32) + "+" +
            "-".repeat(17) + "+" +
            "-".repeat(13) + "+";

    public Ledger() {
        masterCopy = new ArrayList<>();
    }

    /*-----Methods-----*/

    public void postToLedger(LocalDateTime timeStamp, String description, String vendor, double amount) {
        masterCopy.add(new Transaction(timeStamp, description, vendor, amount));
    }

    /*-----Display Methods-----*/

    public void displayAsTable() {
        displayAsTable(DEFAULT_FORMAT, masterCopy);
    }

    public void displayAsTable(ArrayList<Transaction> ledger) {
        displayAsTable(DEFAULT_FORMAT, ledger);
    }

    public void displayAsTable(String format, ArrayList<Transaction> ledger) {
        displayTableHeader();
        for(int i = ledger.size() - 1; i >= 0; i--) {
            displayTableEntry(ledger.get(i).toTableFormat(format));
        }
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

    /*-----Getters-----*/

    public ArrayList<Transaction> getMasterCopy() {
        return masterCopy;
    }
}
