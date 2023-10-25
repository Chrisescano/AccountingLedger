package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;

public class Ledger {
    private final LedgerFileManager fileManager;
    private final ArrayList<Transaction> masterCopy;
    private String dateFormat = "%10s";
    private String timeFormat = "%8s";
    private String descriptionFormat = "%-30.30s";
    private String vendorFormat = "%-15.15s";
    private String amountFormat = "$%10.10s";
    private String dateColor = "cyan";
    private String timeColor = "yellow";
    private String descriptionColor = "";
    private String vendorColor = "";
    private String amountColor = "";


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
        displayAsTable(masterCopy);
    }

    public void displayAsTable(ArrayList<Transaction> ledger) {
        displayTableHeader();
        for(int i = ledger.size() - 1; i >= 0; i--) {
            amountColor = ledger.get(i).amount() > -1 ? "green" : "red";
            String tableFormat = Terminal.colorTableFormat(
                    dateColor,timeColor,descriptionColor,vendorColor,amountColor,
                    dateFormat,timeFormat,descriptionFormat,vendorFormat,amountFormat
            );

            displayTableEntry(String.format(
                    tableFormat,
                    ledger.get(i).getDate(),
                    ledger.get(i).getTime(),
                    ledger.get(i).description(),
                    ledger.get(i).vendor(),
                    ledger.get(i).getAmount()
                    )
            );
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

    /*-----Setters-----*/

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public void setDescriptionFormat(String descriptionFormat) {
        this.descriptionFormat = descriptionFormat;
    }

    public void setVendorFormat(String vendorFormat) {
        this.vendorFormat = vendorFormat;
    }

    public void setAmountFormat(String amountFormat) {
        this.amountFormat = amountFormat;
    }

    public void setDateColor(String dateColor) {
        this.dateColor = dateColor;
    }

    public void setTimeColor(String timeColor) {
        this.timeColor = timeColor;
    }

    public void setDescriptionColor(String descriptionColor) {
        this.descriptionColor = descriptionColor;
    }

    public void setVendorColor(String vendorColor) {
        this.vendorColor = vendorColor;
    }

    public void setAmountColor(String amountColor) {
        this.amountColor = amountColor;
    }
}
