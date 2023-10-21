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
            System.out.print("\nHere are the commands you can do:\n  (D) - Add a deposit\n  (P) - Make a payment\n" +
                    "  (L) - View your ledger\n  (X) - Exit from the program\nType in your command: ");
            char command = ImprovedIO.getCharInput();
            switch(command) {
                case 'D':
                    addDeposit();
                    break;
                case 'P':
                    makePayment();
                    break;
                case 'L':
                    ledgerScreenPrompt();
                    break;
                case 'X':
                    System.out.println("You want to exit from the program");
                    return;
                default:
                    System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }

    public static void ledgerScreenPrompt() {
        while(true) {
            System.out.print("\nHere are the commands you can do:\n  (A) - Display all ledger posts\n" +
                    "  (D) - Display only deposits\n  (P) - Display only payments\n  (R) - Filter ledger by defined values\n" +
                    "  (H) - Go back to the home menu\nType in your command: ");
            char command = ImprovedIO.getCharInput();
            switch(command) {
                case 'A':
                    System.out.println("Fetching all ledger posts...\n");
                    ledger.displayAllLedgerPosts();
                    break;
                case 'D':
                    System.out.println("Display only deposits");
                    break;
                case 'P':
                    System.out.println("Display only payments");
                    break;
                case 'R':
                    System.out.println("You want to filter the ledger by defined values");
                    break;
                case 'H':
                    System.out.println("You want to go back to the home menu");
                    return;
                default:
                    System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }

    public static void reportsScreenPrompt() {
        while(true) {
            System.out.print("Here are the commands you can do:\n  1. Month to Date\n  2. Previous Month\n" +
                    "  3. Year to Date\n  4. Previous Year\n  5. Search by vendor\n  6. Go back to ledger screen\n" +
                    "Type in your command: ");
            int command = ImprovedIO.getIntInput();
            switch(command) {
                case 1:
                    System.out.println("You want to filter by month to date");
                    break;
                case 2:
                    System.out.println("You want to filter by previous month");
                    break;
                case 3:
                    System.out.println("Ypu want to filter by year to date");
                    break;
                case 4:
                    System.out.println("You want to filter by previous year");
                    break;
                case 5:
                    System.out.println("You want to filter by vendor");
                    break;
                case 6:
                    System.out.println("You want to go back to the ledger screen");
                    return;
                default:
                    System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }

    private static void customSearchPrompt() {
        while(true) {
            System.out.print("Here are the commands you can do:\n  1. Search by start date\n  2. Search by end date\n" +
                    "  3. Search by description\n  4. Search by vendor\n  5. Search by amount\n" +
                    "  6. Go back to the ledger screen menu\nType in your command: ");
            int command = ImprovedIO.getIntInput();
            switch(command) {
                case 1:
                    System.out.println("You want to filter by start date");
                    break;
                case 2:
                    System.out.println("You want to filter by end date");
                    break;
                case 3:
                    System.out.println("You want to filter by description");
                    break;
                case 4:
                    System.out.println("You want to filter by vendor");
                    break;
                case 5:
                    System.out.println("You want to filter by amount");
                    break;
                case 6:
                    System.out.println("You want to go back to the ledger screen");
                    break;
                default:
                    System.out.println("Sorry, that is not a valid command. Please try again");
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

        ledger.postToLedger(localDateTime, descriptionInput, vendorInput, amountInput);
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
        if(amountInput > 0) amountInput *= -1;

        ledger.postToLedger(localDateTime, descriptionInput, vendorInput, amountInput);
    }
}
