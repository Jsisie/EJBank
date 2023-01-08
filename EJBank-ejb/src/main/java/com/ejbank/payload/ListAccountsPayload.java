package com.ejbank.payload;

import java.util.List;

public class ListAccountsPayload {
    private List<AccountsPayload> accounts;
    private String error;

    public ListAccountsPayload(List<AccountsPayload> accounts) {
        this.accounts = accounts;
    }

    public ListAccountsPayload(String error) {
        this.accounts = List.of();
        this.error = error;
    }

    public List<AccountsPayload> getAccounts() {
        return accounts;
    }

    public String getError() {
        return error;
    }
}
