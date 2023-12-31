package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class AppCLI {

    private static final Ledger ledger = new Ledger();

    public static void main(String[] args) {
        new Sorter(ledger.getMasterCopy());
        new LedgerStats(ledger.getMasterCopy());

        ledger.init();
        ledger.load();
        Terminal.printColor("blue", "Welcome to your accounting ledger!\n");
        homeScreenPrompt();
    }

    /*-----Main Command Menus-----*/

    public static void homeScreenPrompt() {
        while (true) {
            Terminal.printHomeMenu("BLUE", "CYAN", "");
            char command = ImprovedIO.getCharInput();
            switch (command) {
                case 'D' -> makeTransaction(true);
                case 'P' -> makeTransaction(false);
                case 'L' -> ledgerScreenPrompt();
                case 'X' -> {
                    System.out.println("Thank you for visiting. Please come again!");
                    return;
                }
                default -> Terminal.printColor("red", "Sorry, that is not a valid command. Please try again\n");
            }
        }
    }

    public static void ledgerScreenPrompt() {
        while (true) {
            Terminal.printLedgerMenu("blue", "cyan", "");
            char command = ImprovedIO.getCharInput();
            switch (command) {
                case 'A' -> {
                    Terminal.printColor("green", "\nFetching All Ledger Transactions...\n");
                    ledger.displayAsTable();
                }
                case 'D' -> {
                    Terminal.printColor("green", "\nFetching All Ledger Deposits...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.byTransactionType(true);
                    ledger.displayAsTable(sortedLedger);
                }
                case 'P' -> {
                    Terminal.printColor("green", "\nFetching all Ledger Payments...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.byTransactionType(false);
                    ledger.displayAsTable(sortedLedger);
                }
                case 'R' -> reportsScreenPrompt();
                case 'S' -> statsScreenPrompt();
                case 'H' -> {
                    return;
                }
                default -> Terminal.printColor("red", "Sorry, that is not a valid command. Please try again\n");
            }
        }
    }

    public static void reportsScreenPrompt() {
        while (true) {
            Terminal.printReportMenu("blue", "cyan", "");
            int command = ImprovedIO.getIntInput();
            switch (command) {
                case 1 -> {
                    Terminal.printColor("green", "\nFiltering From Month to Date...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.byDate(LocalDateTime.now(), true);
                    ledger.displayAsTable(sortedLedger);
                }
                case 2 -> {
                    Terminal.printColor("green", "\nFiltering Last Month's Transactions...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.byDate(LocalDateTime.now().minusMonths(1), true);
                    ledger.displayAsTable(sortedLedger);
                }
                case 3 -> {
                    Terminal.printColor("green", "\nFiltering From Year to Date...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.byDate(LocalDateTime.now(), false);
                    ledger.displayAsTable(sortedLedger);
                }
                case 4 -> {
                    Terminal.printColor("green", "\nFiltering Last Year's Transactions...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.byDate(LocalDateTime.now().minusYears(1), false);
                    ledger.displayAsTable(sortedLedger);
                }
                case 5 -> {
                    String vendorInput = promptStringInput("Search by vendor: ", false);
                    Terminal.printColor("green", "\nFiltering for ");
                    Terminal.printColor("", vendorInput);
                    Terminal.printColor("green", " vendors...\n");
                    ArrayList<Transaction> sortedLedger = Sorter.byVendor(vendorInput);
                    ledger.displayAsTable(sortedLedger);
                }
                case 6 -> customSearch();
                case 0 -> {
                    return;
                }
                default -> Terminal.printColor("red", "Sorry, that is not a valid command. Please try again\n");
            }
        }
    }

    public static void statsScreenPrompt() {
        while (true) {
            Terminal.printStatsMenu("blue", "cyan", "");
            int command = ImprovedIO.getIntInput();
            switch (command) {
                case 1 -> LedgerStats.displayTotalDeposits();
                case 2 -> {
                    LocalDate date = promptDateInput("Enter the date in YYYY-MM-DD form: ", LocalDate.now());
                    LedgerStats.displaySummaryColumns(date.atTime(LocalTime.MIN), true);
                }
                case 3 -> {
                    LocalDate date = promptDateInput("Enter the date in YYYY-MM-DD form: ", LocalDate.now());
                    LedgerStats.displaySummaryColumns(date.atTime(LocalTime.MIN), false);
                }
                case 4 -> {
                    LocalDate date = promptDateInput("Enter the date in YYYY-MM-DD form: ", LocalDate.now());
                    LedgerStats.displayDetailedYearly(date.atTime(LocalTime.MIN));
                }
                case 5 -> LedgerStats.displayIncomeToDebtTable();
                case 6 -> {
                    return;
                }
                default -> Terminal.printColor("red", "Sorry, that is not a valid command. Please try again\n");
            }
        }
    }

    /*-----Sub Menus-----*/

    public static void makeTransaction(boolean isDeposit) {
        String screenTitle = isDeposit ? "Deposit Screen" : "Payment Screen";
        Terminal.printColor("blue", "\n==========[ " + screenTitle + " ]==========\n");

        LocalDate dateInput = promptDateInput("Date in YYYY-MM-DD format (enter key = today's date): ",
                LocalDate.now());
        LocalTime timeInput = promptTimeInput("Time in HH-MM-SS format (enter key = current time): ",
                LocalTime.now());
        String descriptionInput = promptStringInput("Details (what was it for): ", false);
        String vendorInput = promptStringInput("Vendor: ", false);
        double amountInput = promptDoubleInput("Amount: ");

        LocalDateTime postTimeStamp = dateInput.atTime(timeInput);
        if (isDeposit && amountInput < 0) amountInput *= 1;
        if (!isDeposit && amountInput > 0) amountInput *= -1;

        //cant have blank transactions


        ledger.postToLedger(postTimeStamp, descriptionInput, vendorInput, amountInput);
        ledger.save();
        Terminal.printColor("green", "\nSuccessfully posted to ledger!\n");
    }

    public static void customSearch() {
        System.out.println("\nTo do a custom search please provide the following information:");

        LocalDateTime startDate = promptDateInput("Search by start date: ", LocalDate.MIN).atTime(LocalTime.MIN);
        LocalDateTime endDate = promptDateInput("Search by end date: ", LocalDate.MAX).atTime(LocalTime.MAX);
        String description = promptStringInput("Search by description: ", true);
        String vendor = promptStringInput("Search by vendor: ", true);
        double amount = promptDoubleInput("Search by amount: ", 0);

        ArrayList<Transaction> sortedLedger = Sorter.byCustomSearch(startDate, endDate, description, vendor, amount);
        ledger.displayAsTable(sortedLedger);
    }

    /*-----User prompt and input methods-----*/

    public static String promptStringInput(String prompt, boolean canBeEmpty) {
        String userInput = "";
        do {
            Terminal.printColor("yellow", prompt);
            userInput = ImprovedIO.getLineOfInput();
            if (!userInput.equals("")) canBeEmpty = true;
        } while (!canBeEmpty);
        return userInput;
    }

    public static double promptDoubleInput(String prompt) {
        Terminal.printColor("yellow", prompt);
        return ImprovedIO.getDoubleInput();
    }

    public static double promptDoubleInput(String prompt, double defaultDouble) {
        Terminal.printColor("yellow", prompt);
        String userDoubleInput = ImprovedIO.getLineOfInput();
        if (userDoubleInput.equals("")) return defaultDouble;
        return ImprovedIO.getDoubleInput();
    }

    public static LocalDate promptDateInput(String prompt, LocalDate defaultDate) {
        Terminal.printColor("yellow", prompt);

        LocalDate dateInput = null;
        while (dateInput == null) {
            String userDateInput = ImprovedIO.getLineOfInput();
            if (userDateInput.equals("")) return defaultDate;
            dateInput = ImprovedIO.getDateInput(userDateInput);
        }
        return dateInput;
    }

    public static LocalTime promptTimeInput(String prompt, LocalTime defaultTime) {
        Terminal.printColor("yellow", prompt);

        LocalTime timeInput = null;
        while (timeInput == null) {
            String userTimeInput = ImprovedIO.getLineOfInput();
            if (userTimeInput.equals("")) return defaultTime;
            timeInput = ImprovedIO.getTimeInput(userTimeInput);
        }
        return timeInput;
    }
}
