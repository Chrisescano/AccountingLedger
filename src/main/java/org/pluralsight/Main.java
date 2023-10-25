package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
    private static final Ledger ledger = new Ledger();

    public static void main(String[] args) {
//        ledger.init();
//        ledger.load();
//        System.out.println("Welcome to your accounting ledger!");
//        homeScreenPrompt();

        LocalDateTime mine = LocalDate.MAX.atTime(LocalTime.MAX);
        System.out.println(LocalDateTime.MAX.isEqual(mine));
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
                    Terminal.printColor("green", "\nFetching All Ledger Transactions...\n");
                    ledger.displayAsTable();
                }
                case 'D' -> {
                    Terminal.printColor("green", "\nFetching All Ledger Deposits...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.depositsOnly(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 'P' -> {
                    Terminal.printColor("green", "\nFetching all Ledger Payments...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.paymentsOnly(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 'R' -> reportsScreenPrompt();
                case 'H' -> {return;}
                default -> Terminal.printColor("red", "Sorry, that is not a valid command. Please try again\n");
            }
        }
    }

    public static void reportsScreenPrompt() {
        while(true) {
            Terminal.printReportMenu("blue", "cyan", "");
            int command = ImprovedIO.getIntInput();
            switch (command) {
                case 1 -> {
                    Terminal.printColor("green", "\nFiltering From Month to Date...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.fromMonthToDate(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 2 -> {
                    Terminal.printColor("green", "\nFiltering Last Month's Transactions...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.byPreviousMonth(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 3 -> {
                    Terminal.printColor("green", "\nFiltering From Year to Date...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.fromYearToDate(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 4 -> {
                    Terminal.printColor("green", "\nFiltering Last Year's Transactions...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.byPreviousYear(ledger.getMasterCopy());
                    ledger.displayAsTable(sortedLedger);
                }
                case 5 -> {
                    String vendorInput = promptStringInput("Search by vendor: ");
                    Terminal.printColor("green", "\nFiltering for ");
                    Terminal.printColor("", vendorInput);
                    Terminal.printColor("green", " vendors...\n");
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

        LocalDateTime startDate = promptDateInput("Search by start date", LocalDate.MIN).atTime(LocalTime.MIN);
        LocalDateTime endDate = promptDateInput("Search by end date: ", LocalDate.MAX).atTime(LocalTime.MAX);
        String description = promptStringInput("Search by description: ");
        String vendor = promptStringInput("Search by vendor: ");
        double amount = promptDoubleInput("Search by amount: ", 0);

        ArrayList<Transaction> sortedLedger = Sorter.byCustomSearch(startDate, endDate, description, vendor, amount, ledger.getMasterCopy());
        ledger.displayAsTable(sortedLedger);
    }

    /*-----User prompt and input methods-----*/

    public static String promptStringInput(String prompt) {
        System.out.print(prompt);
        return ImprovedIO.getLineOfInput();
    }

    public static double promptDoubleInput(String prompt) {
        System.out.println(prompt);
        return ImprovedIO.getDoubleInput();
    }

    public static double promptDoubleInput(String prompt, double defaultDouble) {
        System.out.print(prompt);
        String userDoubleInput = ImprovedIO.getLineOfInput();
        if(userDoubleInput.equals("")) return defaultDouble;
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
