package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class LedgerStats {

    private static ArrayList<Transaction> ledger;

    public LedgerStats(ArrayList<Transaction> ledger) {
        this.ledger = ledger;
    }

    public static double[] monthlySummary(LocalDateTime date) {
        ArrayList<Transaction> filteredLedger = Sorter.byDate(date, true);
        return  calculateBalances(filteredLedger);
    }

    public static double[] yearlySummary(LocalDateTime date) {
        ArrayList<Transaction> filteredLedger = Sorter.byDate(date, false);
        return calculateBalances(filteredLedger);
    }

    /*-----Helper Methods-----*/

    private static double[] calculateBalances() {
        return calculateBalances(ledger);
    }

    private static double[] calculateBalances(ArrayList<Transaction> ledger) {
        double depositTotal = 0, paymentTotal = 0;

        for(Transaction transaction : ledger) {
            if(transaction.amount() > 0) depositTotal += transaction.amount();
            else paymentTotal += transaction.amount();
        }
        return new double[] {depositTotal, paymentTotal, depositTotal + paymentTotal};
    }
}
