package org.pluralsight;

public class TerminalManager {

    public static void main(String[] args) {
        printYellow("Hello World");
        ANSI e = ANSI.valueOf("red".toUpperCase());
        System.out.println(e);
    }

    public static void printYellow(String message) {
        System.out.println(ANSI.YELLOW.getCode() + message + ANSI.RESET.getCode());
    }


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
        WHITE("\u001B[37m"),

        //background color codes
        BLACKBG("\u001B[40m"),
        REDBG("\u001B[41m"),
        GREENBG("\u001B[42m"),
        YELLOWBG("\u001B[43m"),
        BLUEBG("\u001B[44m"),
        MAGENTABG("\u001B[45m"),
        CYANBG("\u001B[46m"),
        WHITEBG("\u001B[47m");

        final String terminalCode;

        ANSI(String terminalCode) {
            this.terminalCode = terminalCode;
        }

        public String getCode() {
            return terminalCode;
        }
    }
}
