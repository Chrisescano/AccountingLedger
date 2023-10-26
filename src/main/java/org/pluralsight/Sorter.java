package org.pluralsight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sorter {

    public static ArrayList<Transaction> depositsOnly(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> filteredLedger = filter(
                LocalDateTime.MIN,
                LocalDateTime.MAX,
                "",
                "",
                0,
                true,
                false,
                ledger
        );
        return sortByDateTime(filteredLedger);
    }

    public static ArrayList<Transaction> paymentsOnly(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> filteredLedger = filter(
                LocalDateTime.MIN,
                LocalDateTime.MAX,
                "",
                "",
                0,
                false,
                true,
                ledger
        );
        return sortByDateTime(filteredLedger);
    }

    public static ArrayList<Transaction> fromMonthToDate(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> filteredLedger = filter(
                getStartOfMonth(),
                LocalDateTime.MAX,
                "",
                "",
                0,
                false,
                false,
                ledger
        );
        return sortByDateTime(filteredLedger);
    }

    public static ArrayList<Transaction> byPreviousMonth(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> filteredLedger = filter(
                getStartOfMonth().minusMonths(1).minusSeconds(1),
                getStartOfMonth(),
                "",
                "",
                0,
                false,
                false,
                ledger
        );
        return sortByDateTime(filteredLedger);
    }

    public static ArrayList<Transaction> fromYearToDate(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> filteredLedger = filter(
                getStartOfYear(),
                LocalDateTime.MAX,
                "",
                "",
                0,
                false,
                false,
                ledger
        );
        return sortByDateTime(filteredLedger);
    }

    public static ArrayList<Transaction> byPreviousYear(ArrayList<Transaction> ledger) {
        ArrayList<Transaction> filteredLedger = filter(
                getStartOfYear().minusYears(1).minusSeconds(1),
                getStartOfYear(),
                "",
                "",
                0,
                false,
                false,
                ledger
        );
        return sortByDateTime(filteredLedger);
    }

    public static ArrayList<Transaction> byVendor(String vendor, ArrayList<Transaction> ledger) {
        ArrayList<Transaction> filteredLedger = filter(
                LocalDateTime.MIN,
                LocalDateTime.MAX,
                "",
                vendor,
                0,
                false,
                false,
                ledger
        );
        return sortAlphabetically(filteredLedger);
    }

    public static ArrayList<Transaction> byCustomSearch(LocalDateTime startDate, LocalDateTime endDate,
            String description, String vendor, double amount, ArrayList<Transaction> ledger) {

        ArrayList<Transaction> filteredLedger = filter(startDate, endDate, description, vendor, amount,
                false, false, ledger);
        return sortAlphabetically(filteredLedger);
    }

    /*-----Filtering Method-----*/

    public static ArrayList<Transaction> filter(LocalDateTime startDateTime, LocalDateTime endDateTime, String description,
                                                String vendor, double amount, boolean searchDeposits, boolean searchPayments ,
                                                ArrayList<Transaction> ledger) {
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

    /*-----Helper Functions-----*/

    private static LocalDateTime getStartOfMonth() {
        return LocalDateTime.of(
                LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                1,
                0,
                0,
                0
        );
    }

    private static LocalDateTime getStartOfYear() {
        return LocalDateTime.of(
                LocalDateTime.now().getYear(),
                1,
                1,
                0,
                0,
                0
        );
    }
}
