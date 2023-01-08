package com.ejbank.payload;

import java.util.List;

public class ListTransactionPayload {
    private long total;
    private List<TransactionPayload> transactions;
    private String error;

    public ListTransactionPayload(long total, List<TransactionPayload> transactions) {
        this.total = total;
        this.transactions = transactions;
    }

    public ListTransactionPayload(String error) {
        this.transactions = List.of();
        this.error = error;
    }

    public long getTotal() {
        return total;
    }

    public List<TransactionPayload> getTransactions() {
        return transactions;
    }

    public String getError() {
        return error;
    }
}
