package org.pluralsight;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Ledger {
    private ArrayList<LedgerPost> ledger;

    public Ledger() {
        ledger = new ArrayList<>();
    }

    public void postToLedger(LocalDateTime timeStamp, String description, String vendor, double amount) {
        ledger.add(new LedgerPost(timeStamp, description, vendor, amount));
    }
}
