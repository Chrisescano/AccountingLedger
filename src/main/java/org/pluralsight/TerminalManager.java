package org.pluralsight;

public class TerminalManager {

    public static void main(String[] args) {

    }

    /*-----Print Menu Methods------*/
    public static void printHomeMenu(String titleColor, String commandColor, String descriptionColor) {
        String[] commands = {"  (D) - ", "  (P) - ", "  (L) - ", "  (X) - "};
        String[] descriptions = {"Add a deposit", "Make a payment", "View your ledger", "Exit from the program"};
        printMenu(
                "|==========[ Home Menu ]==========|\n", commands, descriptions,
                titleColor, commandColor, descriptionColor
        );
    }

    public static void printLedgerMenu(String titleColor, String commandColor, String descriptionColor) {
        String[] commands = {"  (A) - ", "  (D) - ", "  (P) - ", "  (R) - ", "  (H) - "};
        String[] descriptions = {"Display all ledger posts","Display only deposits","Display only payments",
                "Filter ledger by pre-defined values","Go back to the home menu"};
        printMenu(
                "|==========[ Ledger Menu ]==========|\n", commands, descriptions,
                titleColor, commandColor, descriptionColor
        );
    }

    public static void printReportMenu(String titleColor, String commandColor, String descriptionColor) {
        String[] commands = {"  (1) - ","  (2) - ","  (3) - ","  (4) - ","  (5) - ","  (6) - ","  (0) - "};
        String[] descriptions = {"Month to Date","Previous Month","Year to Date","Previous Year","Search by vendor",
                "Search by custom values","Go back to ledger screen"};
        printMenu(
                "|==========[ Report Menu ]==========|\n", commands, descriptions,
                titleColor, commandColor, descriptionColor
        );
    }

    public static void printMenu(String title, String[] commmands, String[] descriptions,
            String titleColor, String commandColor, String descriptionColor) {
        System.out.println();
        printColor(toANSICode(titleColor), title);
        for(int i = 0; i < commmands.length; i++) {
            printColor(toANSICode(commandColor), commmands[i]);
            printColor(toANSICode(descriptionColor), descriptions[i] + "\n");
        }
        printColor(toANSICode(titleColor), "Type in your command: ");
    }

    public static String toANSICode(String color) {
        try {
            return ANSI.valueOf(color.toUpperCase()).terminalCode;
        } catch(IllegalArgumentException e) {
            return "";
        }
    }

    /*-----Print Text Color Methods-----*/

//    public static void printRed(String message) { printColor(ANSI.RED.terminalCode, message); }
//
//    public static void printGreen(String message) { printColor(ANSI.GREEN.terminalCode, message); }
//
//    public static void printYellow(String message) { printColor(ANSI.YELLOW.terminalCode, message); }
//
//    public static void printBlue(String message) { printColor(ANSI.BLUE.terminalCode, message); }

    public static void printColor(String color, String message) {
        System.out.print(color + message + ANSI.RESET.terminalCode);
    }

    /*-----Private ENUM class----*/

    private enum ANSI {
        //reset code
        RESET("\u001B[0m"),

        //text color codes
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        MAGENTA("\u001B[35m"),
        CYAN("\u001B[36m"),

        //background color codes
        BLACKBG("\u001B[40m"),
        REDBG("\u001B[41m"),
        GREENBG("\u001B[42m"),
        YELLOWBG("\u001B[43m"),
        BLUEBG("\u001B[44m");

        private final String terminalCode;

        ANSI(String terminalCode) {
            this.terminalCode = terminalCode;
        }
    }
}
