package org.pluralsight;

import java.util.ArrayList;

public class Ledger {
    private ArrayList<LedgerPost> ledger;

    public Ledger() {
        ledger = new ArrayList<>();
    }

    public void postToLedger(int year, int month, int day, int hour, int minute, int second,
                             String description, String vendor, double amount) {
        ledger.add(new LedgerPost(year, month, day, hour, minute, second, description, vendor, amount));
    }
}
