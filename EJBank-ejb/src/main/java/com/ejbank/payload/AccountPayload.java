package com.ejbank.payload;

public class AccountPayload {
    private String owner;
    private String advisor;
    private int rate;
    private int interest;
    private int amount;
    private String error;

    public AccountPayload(String ownerFirstName, String ownerLastName,
                          String advisorFirstName, String advisorLastName,
                          int rate, int interest, int amount, String error) {
        this.owner = ownerFirstName + " " + ownerLastName;
        this.advisor = advisorFirstName + " " + advisorLastName;
        this.rate = rate;
        this.interest = interest;
        this.amount = amount;
        this.error = error;
    }

    public String getOwner() {
        return owner;
    }

    public String getAdvisor() {
        return advisor;
    }

    public int getRate() {
        return rate;
    }

    public int getInterest() {
        return interest;
    }

    public int getAmount() {
        return amount;
    }

    public String getError() {
        return error;
    }
}
