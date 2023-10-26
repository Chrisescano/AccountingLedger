package org.pluralsight;

public class Terminal {

    /*-----Print Menu Methods------*/
    public static void printHomeMenu(String titleColor, String commandColor, String descriptionColor) {
        String[] commands = {"  (D) - ", "  (P) - ", "  (L) - ", "  (X) - "};
        String[] descriptions = {"Add a deposit", "Make a payment", "View your ledger", "Exit from the program"};
        printMenu("==========[ Home Screen ]==========", commands, descriptions,
                titleColor, commandColor, descriptionColor
        );

    }

    public static void printLedgerMenu(String titleColor, String commandColor, String descriptionColor) {
        String[] commands = {"  (A) - ", "  (D) - ", "  (P) - ", "  (R) - ", "  (H) - "};
        String[] descriptions = {"Display all ledger posts","Display only deposits","Display only payments",
                "Filter ledger by pre-defined values","Go back to the home menu"};
        printMenu("==========[ Ledger Screen ]==========", commands, descriptions,
                titleColor, commandColor, descriptionColor
        );
    }

    public static void printReportMenu(String titleColor, String commandColor, String descriptionColor) {
        String[] commands = {"  (1) - ","  (2) - ","  (3) - ","  (4) - ","  (5) - ","  (6) - ","  (0) - "};
        String[] descriptions = {"Month to Date","Previous Month","Year to Date","Previous Year","Search by vendor",
                "Search by custom values","Go back to ledger screen"};
        printMenu("==========[ Report Screen ]==========", commands, descriptions,
                titleColor, commandColor, descriptionColor
        );
    }

    public static void printMenu(String title, String[] commands, String[] descriptions,
                                 String titleColor, String commandColor, String descriptionColor) {
        printColor(titleColor, "\n" + title + "\n");
        for(int i = 0; i < commands.length; i++) {
            printColor(commandColor, commands[i]);
            printColor(descriptionColor, descriptions[i] + "\n");
        }
        printColor(titleColor, "Type in your command: ");
    }

    public static String colorTableFormat(
            String dateColor, String timeColor, String descriptionColor, String vendorColor, String amountColor,
            String dateFormat, String timeFormat, String descriptionFormat, String vendorFormat, String amountFormat) {

        String cDateFormat = wrapString(dateColor, dateFormat);
        String cTimeFormat = wrapString(timeColor, timeFormat);
        String cDescriptionFormat = wrapString(descriptionColor, descriptionFormat);
        String cVendorFormat = wrapString(vendorColor, vendorFormat);
        String cAmountFormat = wrapString(amountColor, amountFormat);
        return "| " + cDateFormat + " @ " + cTimeFormat + " | " + cDescriptionFormat + " | " + cVendorFormat + " | " + cAmountFormat + " |";
    }

    /*-----Print Text Color Method-----*/

    private static String toANSICode(String color) {
        try {
            return ANSI.valueOf(color.toUpperCase()).terminalCode;
        } catch(IllegalArgumentException e) {
            return "";
        }
    }

    public static void printColor(String color, String message) {
        System.out.print(toANSICode(color) + message + ANSI.RESET.terminalCode);
    }

    public static String wrapString(String color, String message) {
        String colorString = toANSICode(color) + message + ANSI.RESET.terminalCode;
        return colorString;
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
        BLUEBG("\u001B[44m"),

        //text formatting
        BOLD("\u001B[1m"),
        UNDERLINE("\u001B[4m");

        private final String terminalCode;

        ANSI(String terminalCode) {
            this.terminalCode = terminalCode;
        }
    }
}
