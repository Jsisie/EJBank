package com.ejbank.payload;

public class TransactionResponsePayLoad {
    private boolean result;
    private int before;
    private int after;
    private String message;
    private String error;

    public TransactionResponsePayLoad(boolean result, int before, int after, String message) {
        this.result = result;
        this.before = before;
        this.after = after;
        this.message = message;
    }

    public TransactionResponsePayLoad(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public TransactionResponsePayLoad(String error) {
        this.error = error;
    }

    public boolean isResult() {
        return result;
    }

    public int getBefore() {
        return before;
    }

    public int getAfter() {
        return after;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
