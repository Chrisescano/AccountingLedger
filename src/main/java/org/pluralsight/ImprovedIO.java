package org.pluralsight;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ImprovedIO {
    private static Scanner scanner = new Scanner(System.in);

    public static String getLineOfInput() {
        return scanner.nextLine();
    }

    public static String getWordOfInput() {
        String input = scanner.next();
        scanner.nextLine();
        return input;
    }

    public static char getCharInput() {
        char input = scanner.next().toUpperCase().charAt(0);
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

    public static String readFromFile(String fileName) {
        String file = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String fileLine;
            while ((fileLine = bufferedReader.readLine()) != null) {
                file += fileLine;
            }
        } catch(IOException e) {
            System.out.println("Oops, looks like I could not read from the file: " + fileName);
            e.printStackTrace();
        }
        return file;
    }

    public static void writeToFile(String fileName, String[] file) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            for(int i = 0; i < file.length; i++) {
                bufferedWriter.write(file[i]);
            }
        } catch(IOException e) {
            System.out.println("Oops, looks like I could not write to this file: " + fileName);
            e.printStackTrace();
        }
    }
}
