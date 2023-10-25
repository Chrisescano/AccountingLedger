package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
    private static final Ledger ledger = new Ledger();

    public static void main(String[] args) {
        ledger.init();
        ledger.load();
        System.out.println("Welcome to your accounting ledger!");
        homeScreenPrompt();
    }

    /*-----Main Command Menus-----*/

    public static void homeScreenPrompt() {
        while(true) {
            Terminal.printHomeMenu("BLUE", "CYAN", "");
            char command = ImprovedIO.getCharInput();
            switch (command) {
                case 'D' -> addDeposit();
                case 'P' -> makePayment();
                case 'L' -> ledgerScreenPrompt();
                case 'X' -> {
                    System.out.println("Thank you for visiting. Please come again!");
                    return;
                }
                default -> System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }

    public static void ledgerScreenPrompt() {
        while(true) {
            Terminal.printLedgerMenu("blue", "cyan", "");
            char command = ImprovedIO.getCharInput();
            switch (command) {
                case 'A' -> {
                    System.out.println("\nFetching all ledger posts...");
                    ledger.displayAsTable();
                }
                case 'D' -> {
                    System.out.println("\nFetching all deposits...");
                    ArrayList<Transaction> sortedLedger = Sorter.depositsOnly(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 'P' -> {
                    System.out.println("\nFetching all payments...");
                    ArrayList<Transaction> sortedLedger = Sorter.paymentsOnly(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 'R' -> reportsScreenPrompt();
                case 'H' -> {return;}
                default -> System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }

    public static void reportsScreenPrompt() {
        while(true) {
            Terminal.printReportMenu("blue", "cyan", "");
            int command = ImprovedIO.getIntInput();
            switch (command) {
                case 1 -> {
                    System.out.println("\nFiltering from Month to Date...");
                    ArrayList<Transaction> sortedLedger = Sorter.fromMonthToDate(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 2 -> {
                    System.out.println("\nFiltering last months posts....");
                    ArrayList<Transaction> sortedLedger = Sorter.byPreviousMonth(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 3 -> {
                    System.out.println("\nFiltering from Year to Date...");
                    ArrayList<Transaction> sortedLedger = Sorter.fromYearToDate(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 4 -> {
                    System.out.println("\nFiltering last years posts...");
                    ArrayList<Transaction> sortedLedger = Sorter.byPreviousYear(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 5 -> {
                    String vendorInput = promptStringInput("Search by vendor: ");
                    System.out.printf("\nFiltering by vendors: %s\n", vendorInput);
                    ArrayList<Transaction> sortedLedger = Sorter.byVendor(vendorInput, ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 6 -> customSearch();
                case 0 -> {return;}
                default -> System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }

    /*-----Sub Menus-----*/

    public static void addDeposit() {
        System.out.println("\nTo post a deposit please provide the following information: ");

        LocalDate dateInput = promptDateInput("Date in YYYY-MM-DD format (enter key = today's date): ",
                LocalDate.now());
        LocalTime timeInput = promptTimeInput("Time in HH-MM-SS format (enter key = current time): ",
                LocalTime.now());
        String descriptionInput = promptStringInput("Details(what was it for): ");
        String vendorInput = promptStringInput("Deposit from: ");
        double amountInput = promptDoubleInput("Amount: ");

        LocalDateTime postTimeStamp = dateInput.atTime(timeInput);
        if(amountInput < 0) amountInput *= -1;

        ledger.postToLedger(postTimeStamp, descriptionInput, vendorInput, amountInput);
        ledger.save();
        System.out.println("\nSuccessfully posted to ledger!");
    }

    public static void makePayment() {
        System.out.println("\nTo post a payment please provide the following information");

        LocalDate dateInput = promptDateInput("Date in YYYY-MM-DD format (enter key = today's date): ",
                LocalDate.now());
        LocalTime timeInput = promptTimeInput("Time in HH-MM-SS format (enter key = current time): ",
                LocalTime.now());
        String descriptionInput = promptStringInput("Details (what was it for): ");
        String vendorInput = promptStringInput("Pay to: ");
        double amountInput = promptDoubleInput("Amount: ");

        LocalDateTime postTimeStamp = dateInput.atTime(timeInput);
        if(amountInput > 0) amountInput *= -1;

        ledger.postToLedger(postTimeStamp, descriptionInput, vendorInput, amountInput);
        ledger.save();
        System.out.println("\nSuccessfully posted to ledger!");
    }

    public static void customSearch() {
        System.out.println("\nTo do a custom search please provide the following information:");

        String startDate = promptStringInput("Search by start of date");
        String endDate = promptStringInput("Search by end date: ");
        String description = promptStringInput("Search by description: ");
        String vendor = promptStringInput("Search by vendor: ");
        String amount = promptStringInput("Search by amount: ");

        ArrayList<Transaction> sortedLedger = Sorter.byCustomSearch(startDate, endDate, description, vendor, amount,
                ledger.getMasterCopy());
        ledger.displayAsTable(sortedLedger);
    }

    /*-----User prompt and input methods-----*/

    public static String promptStringInput(String prompt) {
        System.out.print(prompt);
        return ImprovedIO.getLineOfInput();
    }

    public static double promptDoubleInput(String prompt) {
        System.out.print(prompt);
        return ImprovedIO.getDoubleInput();
    }

    public static LocalDate promptDateInput(String prompt, LocalDate defaultDate) {
        System.out.print(prompt);
        String userDateInput = ImprovedIO.getLineOfInput();
        if(userDateInput.equals("")) return defaultDate;
        return ImprovedIO.getDateInput(userDateInput);
    }

    public static LocalTime promptTimeInput(String prompt, LocalTime defaultTime) {
        System.out.print(prompt);
        String userTimeInput = ImprovedIO.getLineOfInput();
        if(userTimeInput.equals("")) return defaultTime;
        return ImprovedIO.getTimeInput(userTimeInput);
    }
}
