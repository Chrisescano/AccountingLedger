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

    public Ledger() {
        ledgerMasterCopy = new ArrayList<>();
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
        for(int i = ledgerMasterCopy.size() - 1; i > -1; i--) {
            displayTableEntry(ledgerMasterCopy.get(i).toTableFormat(format));
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

    public ArrayList<Transaction> getLedgerMasterCopy() {
        return ledgerMasterCopy;
    }
}
