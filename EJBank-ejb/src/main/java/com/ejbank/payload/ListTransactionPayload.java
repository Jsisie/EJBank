package com.ejbank.payload;

import java.util.List;

public class ListTransactionPayload {
    private int total;
    private List<TransactionPayload> transactions;
    private String error;

    public ListTransactionPayload(int total, List<TransactionPayload> transactions) {
        this.total = total;
        this.transactions = transactions;
    }

    public ListTransactionPayload(String error) {
        this.error = error;
    }

    public int getTotal() {
        return total;
    }

    public List<TransactionPayload> getTransactions() {
        return transactions;
    }

    public String getError() {
        return error;
    }
}
