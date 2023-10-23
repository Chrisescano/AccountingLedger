package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {
    static Ledger ledger = new Ledger();

    public static void main(String[] args) {
        System.out.println("Welcome to your accounting ledger!");
        homeScreenPrompt();
    }

    /*-----Main Command Menus-----*/

    public static void homeScreenPrompt() {
        while(true) {
            System.out.print("""

                    Currently in: Home Screen Menu
                    Here are the commands you can do:
                      (D) - Add a deposit
                      (P) - Make a payment
                      (L) - View your ledger
                      (X) - Exit from the program
                    Type in your command:\s""");
            char command = ImprovedIO.getCharInput();
            switch (command) {
                case 'D' -> addDeposit();
                case 'P' -> makePayment();
                case 'L' -> ledgerScreenPrompt();
                case 'X' -> {
                    System.out.println("You want to exit from the program");
                    return;
                }
                default -> System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }

    public static void ledgerScreenPrompt() {
        while(true) {
            System.out.print("""

                    Currently in: Ledger Screen Menu
                    Here are the commands you can do:
                      (A) - Display all ledger posts
                      (D) - Display only deposits
                      (P) - Display only payments
                      (R) - Filter ledger by pre-defined values
                      (H) - Go back to the home menu
                    Type in your command:\s""");
            char command = ImprovedIO.getCharInput();
            switch (command) {
                case 'A' -> {
                    System.out.println("\nFetching all ledger posts...");
                    ledger.displayLedgerAsTable();
                }
                case 'D' -> {
                    System.out.println("\nFetching all deposits...");
                    ArrayList<Transaction> sortedLedger = Sorter.depositsOnly(ledger.getLedgerMasterCopy());
                    ledger.displayLedgerAsTable(sortedLedger);
                }
                case 'P' -> {
                    System.out.println("\nFetching all payments...");
                    ArrayList<Transaction> sortedLedger = Sorter.paymentsOnly(ledger.getLedgerMasterCopy());
                    ledger.displayLedgerAsTable(sortedLedger);
                }
                case 'R' -> reportsScreenPrompt();
                case 'H' -> {return;}
                default -> System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }

    public static void reportsScreenPrompt() {
        while(true) {
            System.out.print("""
                    
                    Currently in: Predefined Filter Menu
                    Here are the commands you can do:
                      (1) Month to Date
                      (2) Previous Month
                      (3) Year to Date
                      (4) Previous Year
                      (5) Search by vendor
                      (6) Go back to ledger screen
                    Type in your command:\s""");
            int command = ImprovedIO.getIntInput();
            switch (command) {
                case 1 -> {
                    System.out.println("\nFiltering from Month to Date...");
                    ArrayList<Transaction> sortedLedger = Sorter.fromMonthToDate(ledger.getLedgerMasterCopy());
                    ledger.displayLedgerAsTable(sortedLedger);
                }
                case 2 -> {
                    System.out.println("\nFiltering last months posts....");
                    ArrayList<Transaction> sortedLedger = Sorter.byPreviousMonth(ledger.getLedgerMasterCopy());
                    ledger.displayLedgerAsTable(sortedLedger);
                }
                case 3 -> {
                    System.out.println("\nFiltering from Year to Date...");
                    ArrayList<Transaction> sortedLedger = Sorter.fromYearToDate(ledger.getLedgerMasterCopy());
                    ledger.displayLedgerAsTable(sortedLedger);
                }
                case 4 -> {
                    System.out.println("\nFiltering last years posts...");
                    ArrayList<Transaction> sortedLedger = Sorter.byPreviousYear(ledger.getLedgerMasterCopy());
                    ledger.displayLedgerAsTable(sortedLedger);
                }
                case 5 -> {
                    String vendorInput = promptStringInput("Search by vendor: ");
                    System.out.printf("\nFiltering by vendors: %s\n", vendorInput);
                    ArrayList<Transaction> sortedLedger = Sorter.byVendor(vendorInput, ledger.getLedgerMasterCopy());
                    ledger.displayLedgerAsTable(sortedLedger);
                }
                case 6 -> {return;}
                default -> System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }

    private static void customSearchPrompt() {
        while(true) {
            System.out.print("""
                    
                    Here are the commands you can do:
                      1. Search by start date
                      2. Search by end date
                      3. Search by description
                      4. Search by vendor
                      5. Search by amount
                      6. Go back to the ledger screen menu
                    Type in your command:\s""");
            int command = ImprovedIO.getIntInput();
            switch (command) {
                case 1 -> System.out.println("You want to filter by start date");
                case 2 -> System.out.println("You want to filter by end date");
                case 3 -> System.out.println("You want to filter by description");
                case 4 -> System.out.println("You want to filter by vendor");
                case 5 -> System.out.println("You want to filter by amount");
                case 6 -> System.out.println("You want to go back to the ledger screen");
                default -> System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }

    /*-----Sub Menus-----*/

    public static void addDeposit() {
        System.out.println("\nTo post a deposit please provide the following information: ");

        LocalDate dateInput = promptDateInput("Date in YYYY-MM-DD format (enter key = today's date): ");
        LocalTime timeInput = promptTimeInput("Time in HH-MM-SS format (enter key = current time): ");
        String descriptionInput = promptStringInput("Details(what was it for): ");
        String vendorInput = promptStringInput("Deposit from: ");
        double amountInput = promptDoubleInput("Amount: ");

        LocalDateTime postTimeStamp = dateInput.atTime(timeInput);
        if(amountInput < 0) amountInput *= -1;

        ledger.postToLedger(postTimeStamp, descriptionInput, vendorInput, amountInput);
        System.out.println("\nSuccessfully posted to ledger!");
    }

    public static void makePayment() {
        System.out.println("\nTo post a payment please provide the following information: ");

        LocalDate dateInput = promptDateInput("Date in YYYY-MM-DD format (enter key = today's date): ");
        LocalTime timeInput = promptTimeInput("Time in HH-MM-SS format (enter key = current time): ");
        String descriptionInput = promptStringInput("Details (what was it for): ");
        String vendorInput = promptStringInput("Pay to: ");
        double amountInput = promptDoubleInput("Amount: ");

        LocalDateTime postTimeStamp = dateInput.atTime(timeInput);
        if(amountInput > 0) amountInput *= -1;

        ledger.postToLedger(postTimeStamp, descriptionInput, vendorInput, amountInput);
        System.out.println("\nSuccessfully posted to ledger!");
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

    public static LocalDate promptDateInput(String prompt) {
        System.out.print(prompt);
        return ImprovedIO.getDateInput();
    }

    public static LocalTime promptTimeInput(String prompt) {
        System.out.print(prompt);
        return ImprovedIO.getTimeInput();
    }
}
