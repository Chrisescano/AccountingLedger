package org.pluralsight;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ImprovedIO {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean canTimeInputBeAnything = false;

    /*-----I/O Methods-----*/

    public static String getLineOfInput() {
        return scanner.nextLine();
    }

    public static String getWordOfInput() {
        String input = scanner.next();
        scanner.nextLine();
        return input;
    }

    public static char getCharInput() {
        return getWordOfInput().toUpperCase().charAt(0);
    }

    public static int getIntInput() {
        int input;
        while(true) {
            try {
                input = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Oops, expecting an integer value. Please try again");
                scanner.nextLine();
            }
        }
        return input;
    }

    public static double getDoubleInput() {
        double input;
        while(true) {
            try {
                input = scanner.nextDouble();
                scanner.nextLine();
                break;
            } catch(InputMismatchException e) {
                System.out.println("Oops, expecting an decimal value. Please try again");
                scanner.nextLine();
            }
        }
        return input;
    }

    public static double getDoubleInput(String userInput) {
        while(true) {
            try {
                return Double.parseDouble(userInput);
            } catch(InputMismatchException e) {
                System.out.println("Oops, expecting an decimal value. Please try again");
            }
        }
    }

    public static LocalDate getDateInput(String userInput) {
        while(true) {
            try {
                LocalDate dateInput = checkDateFormat(userInput);
                if(dateInput.isAfter(LocalDate.now())) {
                    throw new IllegalDateTimeFormatException(
                            "Oops, cannot post a future transaction to the ledger. Please try again"
                    );
                }
                canTimeInputBeAnything = dateInput.isBefore(LocalDate.now());

                return dateInput;
            } catch (IllegalDateTimeFormatException e) {
                System.out.println(e.getMessage());
            } catch (DateTimeException e) {
                System.out.println("Oops, the date is out of range. Please try again");
            } catch (NumberFormatException e) {
                System.out.println("Oops, a part of the date is not a number. Please try again");
            }
        }
    }

    public static LocalTime getTimeInput(String userInput) {
        while(true) {
            try {
                LocalTime timeInput = checkTimeFormat(userInput);
                if(canTimeInputBeAnything) return timeInput;
                if(LocalTime.now().isBefore(timeInput)) {
                    throw new IllegalDateTimeFormatException(
                            "Oops, cannot post a future transaction to the ledger. Please try again"
                    );
                }

                canTimeInputBeAnything = false;
                return timeInput;
            } catch(IllegalDateTimeFormatException e) {
                System.out.println(e.getMessage());
            } catch(DateTimeException e) {
                System.out.println("Oops, the time is out of range. Please try again");
            } catch (NumberFormatException e) {
                System.out.println("Oops, a part of the time is not a number. Please try again");
            }
        }
    }

    /*-----File Read/Write-----*/

    public static String readFromFile(String fileName) {
        String file = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String fileLine;
            while ((fileLine = bufferedReader.readLine()) != null) {
                file = file.concat(fileLine);
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
            for (String fileLine : file) {
                bufferedWriter.write(fileLine);
            }
            bufferedWriter.close();
        } catch(IOException e) {
            System.out.println("Oops, looks like I could not write to this file: " + fileName);
            e.printStackTrace();
        }
    }

    /*-----Private Methods-----*/

    private static LocalDate checkDateFormat(String date) throws DateTimeException, NumberFormatException, IllegalDateTimeFormatException {
        //checks for '-' chars
        if(!date.contains("-")) {
            throw new IllegalDateTimeFormatException(
                    "The input \"" + date + "\" is missing the '-' delimiters. The correct format is YYYY-MM-DD. Please try again."
            );
        }

        //checks if there are three sections: year, month, and date
        String[] tokens = date.split("-");
        if(tokens.length != 3) {
            throw new IllegalDateTimeFormatException(
                    "The input \"" + date + "\" is in the incorrect format. The correct format is YYYY-MM-DD. Please try again."
            );
        }

        //will throw exception if any date fields out of range, or if non-integer is typed in
        return LocalDate.of(
                Integer.parseInt(tokens[0]),
                Integer.parseInt(tokens[1]),
                Integer.parseInt(tokens[2])
        );
    }

    private static LocalTime checkTimeFormat(String time) throws DateTimeException, NumberFormatException, IllegalDateTimeFormatException {
        if(!time.contains(":")) {       //checks for ':' chars
            throw new IllegalDateTimeFormatException(
                    "The input \"" + time + "\" is missing the ':' delimiters. The correct format is HH-MM-SS. Please try again"
            );
        }

        String[] tokens = time.split(":");
        if(tokens.length != 3) {        //checks if there are three sections: hour, minutes, seconds
            throw new IllegalDateTimeFormatException(
                    "The input \"" + time + "\" is in the incorrect format. The correct format is HH-MM-SS. Please try again"
            );
        }

        //will throw exception if time fields out of range, or if non-integer is typed in
        return LocalTime.of(
                Integer.parseInt(tokens[0]),
                Integer.parseInt(tokens[1]),
                Integer.parseInt(tokens[2])
        );
    }

    /*-----Custom Exception Class-----*/
    private static class IllegalDateTimeFormatException extends Exception {
        public IllegalDateTimeFormatException(String message) {
            super(message);
        }
    }
}
