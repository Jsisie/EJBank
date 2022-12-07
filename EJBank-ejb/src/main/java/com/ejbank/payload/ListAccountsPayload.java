package com.ejbank.payload;

import java.util.List;

public class ListAccountsPayload {
    private List<AccountsPayload> accounts;
    private String error;

    public ListAccountsPayload(List<AccountsPayload> accounts, String error) {
        this.accounts = accounts;
        this.error = error;
    }

    public ListAccountsPayload(List<AccountsPayload> accounts) {
        this.accounts = accounts;
    }

    public List<AccountsPayload> getAccounts() {
        return accounts;
    }

    public String getError() {
        return error;
    }
}
