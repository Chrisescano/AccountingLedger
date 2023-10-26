package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

public class LedgerStats {

    private static ArrayList<Transaction> ledger;

    public LedgerStats(ArrayList<Transaction> ledger) {
        this.ledger = ledger;
    }

    public static void totalBalance() {
        double sum = 0;
        for(Transaction transaction : ledger) { sum += transaction.amount(); }
    }

    public static void monthlySummary(LocalDateTime dateOfInterest) {


    }

    /*-----Helper Methods-----*/

    private static LocalDateTime[] getStartAndEndOfMonth(LocalDateTime dateOfInterest) {
        return new LocalDateTime[]{};
    }
}
