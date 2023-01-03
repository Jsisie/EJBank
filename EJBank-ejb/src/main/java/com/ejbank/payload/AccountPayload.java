package com.ejbank.payload;

public class AccountPayload {
    private String owner;
    private String advisor;
    private Float rate;
    private int interest;
    private Float amount;
    private String error;

    public AccountPayload(String ownerFirstName, String ownerLastName,
                          String advisorFirstName, String advisorLastName,
                          Float rate, int interest, Float amount, String error) {
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

    public Float getRate() {
        return rate;
    }

    public int getInterest() {
        return interest;
    }

    public Float getAmount() {
        return amount;
    }

    public String getError() {
        return error;
    }
}
