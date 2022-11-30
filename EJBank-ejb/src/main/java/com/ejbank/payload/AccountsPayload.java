package com.ejbank.payload;

public class AccountsPayload {
    private int id;
    private String user;
    private String type;
    private int amount;
    private int validation;

    public AccountsPayload(int id,
                           String firstName, String lastName,
                           String type, int amount, int validation) {
        this.id = id;
        this.user = firstName + " " + lastName;
        this.type = type;
        this.amount = amount;
        this.validation = validation;
    }

    public AccountsPayload(int id, String type, int amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public AccountsPayload(int id, String firstName, String lastName, String type, int amount) {
        this.id = id;
        this.user = firstName + " " + lastName;
        this.type = type;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getValidation() {
        return validation;
    }
}
