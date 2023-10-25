package org.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Sorter {

    public static ArrayList<Transaction> depositsOnly(ArrayList<Transaction> ledger) {
        return filter(LocalDateTime.MIN, LocalDateTime.MAX, "", "", 0,
                true, false, ledger);
    }

    public static ArrayList<Transaction> paymentsOnly(ArrayList<Transaction> ledger) {
        return filter(LocalDateTime.MIN, LocalDateTime.MAX, "", "", 0,
                false, true, ledger);
    }

    public static ArrayList<Transaction> fromMonthToDate(ArrayList<Transaction> ledger) {
        return filter(getStartOfMonth(), LocalDateTime.MAX, "", "", 0,
                false, false, ledger);
    }

    public static ArrayList<Transaction> byPreviousMonth(ArrayList<Transaction> ledger) {
        return filter(getStartOfMonth().minusMonths(1).minusSeconds(1), getStartOfMonth(), "", "", 0,
                false, false, ledger);
    }

    public static ArrayList<Transaction> fromYearToDate(ArrayList<Transaction> ledger) {
        return filter(getStartOfYear(), LocalDateTime.MAX, "", "", 0,
                false, false, ledger);
    }

    public static ArrayList<Transaction> byPreviousYear(ArrayList<Transaction> ledger) {
        return filter(getStartOfYear().minusYears(1).minusSeconds(1), getStartOfYear(), "", "", 0,
                false, false, ledger);
    }

    public static ArrayList<Transaction> byVendor(String vendor, ArrayList<Transaction> ledger) {
        return filter(LocalDateTime.MIN, LocalDateTime.MAX, "", vendor, 0,
                false, false, ledger);
    }

    public static ArrayList<Transaction> byCustomSearch(LocalDateTime startDate, LocalDateTime endDate,
            String description, String vendor, double amount, ArrayList<Transaction> ledger) {

        return filter(startDate, endDate, description, vendor, amount, false, false, ledger);
    }

    public static ArrayList<Transaction> filter(LocalDateTime startDateTime, LocalDateTime endDateTime, String description,
                                                String vendor, double amount, boolean searchDeposits, boolean searchPayments ,
                                                ArrayList<Transaction> ledger) {
        ArrayList<Transaction> filteredLedger = new ArrayList<>(ledger);
        if(!startDateTime.equals(LocalDateTime.MIN)) {
            filteredLedger.removeIf(transaction -> transaction.timeStamp().isBefore(startDateTime));
        }

        if(!endDateTime.equals(LocalDateTime.MAX)) {
            filteredLedger.removeIf(transaction -> transaction.timeStamp().isAfter(endDateTime));
        }

        if(!description.equals("")) {
            filteredLedger.removeIf(
                    transaction -> !transaction.description().toLowerCase().contains(description.toLowerCase())
            );
        }

        if(!vendor.equals("")) {
            filteredLedger.removeIf(
                    transaction -> !transaction.vendor().toLowerCase().contains(vendor.toLowerCase())
            );
        }

        if(amount != 0 && !searchDeposits && !searchPayments) filteredLedger.removeIf(transaction -> transaction.amount() != amount);

        if(searchDeposits) filteredLedger.removeIf(transaction -> transaction.amount() < 0);
        else if(searchPayments) filteredLedger.removeIf(transaction -> transaction.amount() > 0);

        return filteredLedger;
    }

    public static void main(String[] args) {
        double amount = 1;
        boolean d = false;
        boolean p = false;

        if(amount != 0 && !d && !p) System.out.println("filter by amount");
        if(d) System.out.println("deposits");
        else if(p) System.out.println("payments");
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
