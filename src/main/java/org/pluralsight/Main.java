package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

                    Here are the commands you can do:
                      (A) - Display all ledger posts
                      (D) - Display only deposits
                      (P) - Display only payments
                      (R) - Filter ledger by defined values
                      (H) - Go back to the home menu
                    Type in your command:\s""");
            char command = ImprovedIO.getCharInput();
            switch (command) {
                case 'A' -> {
                    System.out.println("Fetching all ledger posts...\n");
                    ledger.displayAllLedgerPosts();
                }
                case 'D' -> System.out.println("Display only deposits");
                case 'P' -> System.out.println("Display only payments");
                case 'R' -> System.out.println("You want to filter the ledger by defined values");
                case 'H' -> {
                    System.out.println("You want to go back to the home menu");
                    return;
                }
                default -> System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }

    public static void reportsScreenPrompt() {
        while(true) {
            System.out.print("""
                    
                    Here are the commands you can do:
                      1. Month to Date
                      2. Previous Month
                      3. Year to Date
                      4. Previous Year
                      5. Search by vendor
                      6. Go back to ledger screen
                    Type in your command:\s""");
            int command = ImprovedIO.getIntInput();
            switch (command) {
                case 1 -> System.out.println("You want to filter by month to date");
                case 2 -> System.out.println("You want to filter by previous month");
                case 3 -> System.out.println("Ypu want to filter by year to date");
                case 4 -> System.out.println("You want to filter by previous year");
                case 5 -> System.out.println("You want to filter by vendor");
                case 6 -> {
                    System.out.println("You want to go back to the ledger screen");
                    return;
                }
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

        //add functionality to fetch current localDateTime with enter key
        System.out.print("Date of deposit in YYYY-MM-DD format(enter key = today's date): ");
        LocalDate dateInput = ImprovedIO.getDateInput();

        System.out.print("Time of deposit(enter key = current time): ");
        LocalTime timeInput = ImprovedIO.getTimeInput();
        LocalDateTime localDateTime = dateInput.atTime(timeInput);

        System.out.print("Description of the deposit: ");
        String descriptionInput = ImprovedIO.getLineOfInput();

        System.out.print("Vendor who provided deposit: ");
        String vendorInput = ImprovedIO.getLineOfInput();

        System.out.print("Amount of deposit: ");
        double amountInput = ImprovedIO.getDoubleInput();
        if(amountInput < 0) amountInput *= -1; //ensures if negative will be positive

        ledger.postToLedger(localDateTime, descriptionInput, vendorInput, amountInput);
        System.out.println("\nLedger post successfully added");
    }

    public static void makePayment() {
        System.out.println("\nTo post a payment please provide the following information: ");

        System.out.print("Date of payment in YYYY-MM-DD format(enter key = today's date): ");
        LocalDate dateInput = ImprovedIO.getDateInput();

        //add functionality to fetch current localDateTime with enter key
        System.out.print("Time of payment(enter key = current time): ");
        LocalTime timeInput = ImprovedIO.getTimeInput();
        LocalDateTime localDateTime = dateInput.atTime(timeInput);

        System.out.print("Description of the payment: ");
        String descriptionInput = ImprovedIO.getLineOfInput();

        System.out.print("Payment made to which vendor: ");
        String vendorInput = ImprovedIO.getLineOfInput();

        System.out.print("Amount of payment: ");
        double amountInput = ImprovedIO.getDoubleInput();
        if(amountInput > 0) amountInput *= -1; //ensures if positive, will be negative

        ledger.postToLedger(localDateTime, descriptionInput, vendorInput, amountInput);
        System.out.println("\nLedger post successfully added");
    }
}
