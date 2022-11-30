package com.ejbank.payload;

import java.util.List;

public class TransactionListPayload {
    private int total;
    private List<TransactionPayload> transactions;

    public TransactionListPayload(int total, List<TransactionPayload> transactions) {
        this.total = total;
        this.transactions = transactions;
    }

    public int getTotal() {
        return total;
    }

    public List<TransactionPayload> getTransactions() {
        return transactions;
    }
}
