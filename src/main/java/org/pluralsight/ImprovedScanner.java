package org.pluralsight;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ImprovedScanner {
    private static Scanner scanner = new Scanner(System.in);

    public static String getLineOfInput() {
        return scanner.nextLine();
    }

    public static String getWordOfInput() {
        String input = scanner.next();
        scanner.nextLine();
        return input;
    }

    public static int getIntInput() {
        int input = 0;
        while(true) {
            try {
                input = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Oops, I was expecting an integer value, you typed: "+ input +" Please try again");
                scanner.nextLine();
                //e.printStackTrace(); //testing purposes
            }
        }
        return input;
    }
}
