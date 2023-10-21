package org.pluralsight;
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to your accounting ledger!");
        //homeScreenPrompt();
        ledgerScreenPrompt();
    }

    public static void homeScreenPrompt() {
        while(true) {
            System.out.print("Here are the commands you can do:\n  (D) - Add a deposit\n  (P) - Make a payment\n" +
                    "  (L) - View your ledger\n  (X) - Exit from the program\nType in your command: ");
            char command = ImprovedIO.getCharInput();
            switch(command) {
                case 'D':
                    System.out.println("You want to add a deposit");
                    break;
                case 'P':
                    System.out.println("You want to make a payment");
                    break;
                case 'L':
                    System.out.println("You want to view your ledger");
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
            System.out.print("Here are the commands you can do:\n  (A) - Display all entries\n" +
                    "  (D) - Display only deposits\n  (P) - Display only payments\n  (R) - Filter ledger by defined values\n" +
                    "  (H) - Go back to the home menu\nType command: ");
            char command = ImprovedIO.getCharInput();
            switch(command) {
                case 'A':
                    System.out.println("You want to display all entries");
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
                default:
                    System.out.println("Sorry, that is not a valid command. Please try again");
            }
        }
    }
}
