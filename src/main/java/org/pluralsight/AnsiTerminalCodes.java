package org.pluralsight;

public enum AnsiTerminalCodes {
    //reset code
    RESET("\u001B[0m"),

    //text color codes
    YELLOW("\u001B[30m"),
    BLACK("\u001B[31m"),
    RED("\u001B[32m"),
    GREEN("\u001B[33m"),
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

    AnsiTerminalCodes(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getTerminalCode() {
        return terminalCode;
    }
}
