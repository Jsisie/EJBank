package com.ejbank.payload;

public class AccountPayload {
    private String owner;
    private String advisor;
    private Float rate;
    private double interest;
    private Float amount;
    private String error;

    public AccountPayload(String ownerFirstName, String ownerLastName,
                          String advisorFirstName, String advisorLastName,
                          Float rate, double interest, Float amount) {
        this.owner = ownerFirstName + " " + ownerLastName;
        this.advisor = advisorFirstName + " " + advisorLastName;
        this.rate = rate;
        this.interest = interest;
        this.amount = amount;
    }

    public AccountPayload(String error) {
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

    public double getInterest() {
        return interest;
    }

    public Float getAmount() {
        return amount;
    }

    public String getError() {
        return error;
    }
}
