package org.pluralsight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sorter {

    private static ArrayList<Transaction> ledger;

    public Sorter(ArrayList<Transaction> ledger) {
        this.ledger = ledger;
    }

    public static ArrayList<Transaction> depositsOnly() {
        ArrayList<Transaction> filteredLedger = filter(
                LocalDateTime.MIN,
                LocalDateTime.MAX,
                "",
                "",
                0,
                true,
                false
        );
        return sortByDateTime(filteredLedger);
    }

    public static ArrayList<Transaction> paymentsOnly() {
        ArrayList<Transaction> filteredLedger = filter(
                LocalDateTime.MIN,
                LocalDateTime.MAX,
                "",
                "",
                0,
                false,
                true
        );
        return sortByDateTime(filteredLedger);
    }

    public static ArrayList<Transaction> filterByDate(LocalDateTime date, boolean isMonthRange) {
        LocalDateTime[] dates = isMonthRange ? TimeManager.monthRangeOf(date) : TimeManager.yearRangeOf(date);
        ArrayList<Transaction> filteredLedger = filter(
                dates[0], dates[1], "", "", 0, false, false
        );
        return sortByDateTime(filteredLedger);
    }

    public static ArrayList<Transaction> byVendor(String vendor) {
        ArrayList<Transaction> filteredLedger = filter(
                LocalDateTime.MIN,
                LocalDateTime.MAX,
                "",
                vendor,
                0,
                false,
                false
        );
        return sortAlphabetically(filteredLedger);
    }

    public static ArrayList<Transaction> byCustomSearch(LocalDateTime startDate, LocalDateTime endDate,
            String description, String vendor, double amount, ArrayList<Transaction> ledger) {

        ArrayList<Transaction> filteredLedger = filter(startDate, endDate, description, vendor, amount,
                false, false);
        return sortAlphabetically(filteredLedger);
    }

    /*-----Filtering Method-----*/

    public static ArrayList<Transaction> filter(LocalDateTime startDateTime, LocalDateTime endDateTime, String description,
                                                String vendor, double amount, boolean searchDeposits, boolean searchPayments) {
        ArrayList<Transaction> filteredLedger = new ArrayList<>(ledger);
        if(!startDateTime.equals(LocalDateTime.MIN))
            filteredLedger.removeIf(transaction -> transaction.timeStamp().isBefore(startDateTime));

        if(!endDateTime.equals(LocalDateTime.MAX))
            filteredLedger.removeIf(transaction -> transaction.timeStamp().isAfter(endDateTime));

        if(!description.equals(""))
            filteredLedger.removeIf(
                    transaction -> !transaction.description().toLowerCase().contains(description.toLowerCase())
            );

        if(!vendor.equals(""))
            filteredLedger.removeIf(
                    transaction -> !transaction.vendor().toLowerCase().contains(vendor.toLowerCase())
            );

        if(amount != 0 && !searchDeposits && !searchPayments) filteredLedger.removeIf(transaction -> transaction.amount() != amount);

        if(searchDeposits) filteredLedger.removeIf(transaction -> transaction.amount() < 0);
        else if(searchPayments) filteredLedger.removeIf(transaction -> transaction.amount() > 0);

        return filteredLedger;
    }

    /*-----Sorting Methods-----*/

    public static ArrayList<Transaction> sortByDateTime(ArrayList<Transaction> filteredLedger) {
        Collections.sort(filteredLedger, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.timeStamp().compareTo(o2.timeStamp());
            }
        });
        return filteredLedger;
    }

    public static ArrayList<Transaction> sortAlphabetically(ArrayList<Transaction> filteredLedger) {
        Collections.sort(filteredLedger, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.vendor().compareTo(o2.vendor());
            }
        });
        return filteredLedger;
    }
}
