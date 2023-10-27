package org.pluralsight;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class LedgerStats {

    private static ArrayList<Transaction> ledger;
    private static String[] totalDepositColumns = {"Deposit","Payments","Total"};
    private static String[] incomeToDebtColumns = {"Income To Debt Ratio"};
    private static String[] monthSummaryColumns = {"Month","Deposit","Payments","Total"};
    private static String[] yearSummaryColumns = {"Year","Deposit","Payments","Total"};

    public LedgerStats(ArrayList<Transaction> ledger) {
        this.ledger = ledger;
    }

    public static void main(String[] args) {


    }

    /*-----Display Methods-----*/

    public static void displayDetailedYearly(LocalDateTime date) {
        double[][] detailedSummary = detailedYearlySummary(date);
        System.out.print("""
                    +--------------------+--------------------+--------------------+--------------------+
                    | Month              | Deposits           | Payments           | Total              |
                    +--------------------+--------------------+--------------------+--------------------+
                    """);
        for(int i = 0; i < detailedSummary.length; i++) {
            System.out.println(String.format(
                    "| %-18s | $%17.2f | $%17.2f | $%17.2f |",
                    LocalDate.of(date.getYear(), i + 1, 1).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                    detailedSummary[i][0], detailedSummary[i][1], detailedSummary[i][2]
            ));
            System.out.println("+--------------------+--------------------+--------------------+--------------------+");
        }
    }

    public static void displaySummaryColumns(LocalDateTime date ,boolean isMonthlySummary) {
        double[] summary = isMonthlySummary ? monthlySummary(date) : yearlySummary(date);
        String firstColumn = isMonthlySummary ?
                date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) :
                String.valueOf(date.getYear());
        System.out.println();
        if(isMonthlySummary) {
            System.out.print("""
                    +--------------------+--------------------+--------------------+--------------------+
                    | Month              | Deposits           | Payments           | Total              |
                    +--------------------+--------------------+--------------------+--------------------+
                    """);
        } else {
            System.out.print("""
                    +--------------------+--------------------+--------------------+--------------------+
                    | Year               | Deposits           | Payments           | Total              |
                    +--------------------+--------------------+--------------------+--------------------+
                    """);
        }
        System.out.println(String.format(
                "| %-18s | $%17.2f | $%17.2f | $%17.2f |", firstColumn, summary[0], summary[1], summary[2]
        ));
        System.out.println("+--------------------+--------------------+--------------------+--------------------+");
    }

    public static void displayTotalDeposits() {
        double[] totalBalance = calculateBalances();
        System.out.println();
        System.out.print("""
                +--------------------+--------------------+--------------------+
                | Deposits           | Payments           | Total              |
                +--------------------+--------------------+--------------------+
                """);
        System.out.println(String.format(
                "| $%17.2f | $%17.2f | $%17.2f |", totalBalance[0],totalBalance[1],totalBalance[2]
        ));
        System.out.println("+--------------------+--------------------+--------------------+");
    }

    public static void displayIncomeToDebtTable() {
        System.out.println();
        System.out.print("""
                +--------------------+
                | Ratio              |
                +--------------------+
                """);
        System.out.println(String.format(
                "| %%%17.2f |", debtToIncomeRatio() //need to use % to escape a %
        ));
        System.out.println("+--------------------+");
    }

    /*-----Methods-----*/

    public static double[] monthlySummary(LocalDateTime date) {
        ArrayList<Transaction> filteredLedger = Sorter.byDate(date, true);
        return  calculateBalances(filteredLedger);
    }

    public static double[] yearlySummary(LocalDateTime date) {
        ArrayList<Transaction> filteredLedger = Sorter.byDate(date, false);
        return calculateBalances(filteredLedger);
    }

    public static double[][] detailedYearlySummary(LocalDateTime date) {
        double[][] detailed = new double[12][3];
        for(int i = 0; i < 12; i++) {
            LocalDateTime dateByMonth = LocalDate.of(date.getYear(), i + 1, 1).atTime(LocalTime.MIN);
            ArrayList<Transaction> filteredLedger = Sorter.byDate(dateByMonth, true);
            double[] balancesForThatMonth = calculateBalances(filteredLedger);
            detailed[i][0] = balancesForThatMonth[0];
            detailed[i][1] = balancesForThatMonth[1];
            detailed[i][2] = balancesForThatMonth[2];
        }
        return detailed;
    }

    private static double debtToIncomeRatio() {
        double[] balances = calculateBalances();
        return ((balances[1] * -1) / balances[0]) * 100;
    }

    /*-----Helper Methods-----*/

    public static double[] calculateBalances() {
        return calculateBalances(ledger);
    }

    public static double[] calculateBalances(ArrayList<Transaction> ledger) {
        double depositTotal = 0, paymentTotal = 0;
        for(Transaction transaction : ledger) {
            if(transaction.amount() > 0) depositTotal += transaction.amount();
            else paymentTotal += transaction.amount();
        }
        return new double[] {depositTotal, paymentTotal, depositTotal + paymentTotal};
    }
}
