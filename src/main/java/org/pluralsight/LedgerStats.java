package org.pluralsight;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

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
        LedgerStats.displayTableHeader(new String[]{"hello","world"});

    }

    /*-----Display Methods-----*/

    public static void displayTableHeader(String[] columnTitles) {
        //makes the table divider based on length of columnTitles
        String tableDivider = "";
        for(int i = 0; i < columnTitles.length; i++) {
            tableDivider += "+" + "-".repeat(21);
        }
        tableDivider += "+";

        //creates formatting for different columns
        String[] columnFormats = new String[columnTitles.length];
        for(int i = 0; i < columnTitles.length; i++) {
            //columnFormats[i] = "| %-" + columnTitles[i].length() + "s ";
            columnFormats[i] = "| %-20s";
        }
        columnFormats[columnFormats.length-1] += "|";

        //builds formatted string
        String formattedColumns = "";
        for(int i = 0; i < columnTitles.length; i++) {
            formattedColumns += String.format(columnFormats[i], columnTitles[i]);
        }

        //print everything out
        System.out.println(tableDivider);
        System.out.println(formattedColumns);
        System.out.println(tableDivider);
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
        return (balances[1] / balances[0]) * 100;
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
