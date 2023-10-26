package org.pluralsight;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class LedgerStats {

    private static ArrayList<Transaction> ledger;

    public LedgerStats(ArrayList<Transaction> ledger) {
        this.ledger = ledger;
    }

    public static void main(String[] args) {
        double a = 5;
        double b = 10;

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        System.out.println(decimalFormat.format((a / b) * 100));
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
