package com.ejbank.payload;

public class ServerPayload {
    private boolean result;

    public ServerPayload(boolean result) {
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
