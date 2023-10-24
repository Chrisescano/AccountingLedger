package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Ledger {
    private final LedgerFileManager fileManager;
    private final ArrayList<Transaction> masterCopy;
    private final String DEFAULT_FORMAT = "| %10s @ %8s | %-30.30s | %-15.15s | $%10.2f |";
    private final String tableDivider = "+" +
            "-".repeat(23) + "+" +
            "-".repeat(32) + "+" +
            "-".repeat(17) + "+" +
            "-".repeat(13) + "+";

    public Ledger() {
        this("transactions.csv");
    }

    public Ledger(String fileName) {
        masterCopy = new ArrayList<>();
        fileManager = new LedgerFileManager(fileName);
    }

    /*-----Methods-----*/

    public void postToLedger(LocalDateTime timeStamp, String description, String vendor, double amount) {
        masterCopy.add(new Transaction(timeStamp, description, vendor, amount));
    }

    public void save() {
        fileManager.saveToFile(masterCopy);
    }

    public void load() {
        String[] ledgerStrings = fileManager.loadFromFile();
        for(String transaction : ledgerStrings) {
            String[] tokens = transaction.split("\\|");
            postToLedger(
                    LocalDate.parse(tokens[0]).atTime(LocalTime.parse(tokens[1])),
                    tokens[2],
                    tokens[3],
                    Double.parseDouble(tokens[4])
            );
        }
    }

    public void init() {
        fileManager.makeFile();
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
