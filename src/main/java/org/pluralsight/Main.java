package org.pluralsight;
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to your accounting ledger!");
        homeScreenPrompt();
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
}
