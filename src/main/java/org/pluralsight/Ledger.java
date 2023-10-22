package org.pluralsight;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Ledger {
    private ArrayList<LedgerPost> ledger;
    private String DEFAULT_FORMAT = "| %10s @ %8s | %-30.30s | %-15.15s | $%10.2f |";
    private String tableHeader = "| %-10s @ %-8s | %-30s | %-15s | %-11s |\n";
    private String tableDivider = "+" +
            "-".repeat(23) + "+" +
            "-".repeat(32) + "+" +
            "-".repeat(17) + "+" +
            "-".repeat(13) + "+";

    public Ledger() {
        ledger = new ArrayList<>();
    }

    /*-----Methods-----*/

    public void postToLedger(LocalDateTime timeStamp, String description, String vendor, double amount) {
        ledger.add(new LedgerPost(timeStamp, description, vendor, amount));
    }

    /*-----Display Methods-----*/

    public void displayLedgerAsTable() {
        displayLedgerAsTable(DEFAULT_FORMAT, this.ledger);
    }

    public void displayLedgerAsTable(String format) {
        displayLedgerAsTable(format, this.ledger);
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
        ArrayList<LedgerPost> tmpLedger = ledger;
        tmpLedger.removeIf(ledgerPost -> ledgerPost.getAmount() > 0);
        displayLedgerAsTable(DEFAULT_FORMAT, tmpLedger);
    }

    public void displayDepositsOnly() {
        ArrayList<LedgerPost> tmpLedger = ledger;
        tmpLedger.removeIf(ledgerPost -> ledgerPost.getAmount() < 0);
        displayLedgerAsTable(DEFAULT_FORMAT, tmpLedger);

        for(LedgerPost ledgerPost : ledger) {
            if(ledgerPost.getAmount() < 0) {
                //print ledgerpost as table format
            }
        }
    }
}
