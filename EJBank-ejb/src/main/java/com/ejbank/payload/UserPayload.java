package com.ejbank.payload;

public class UserPayload {
    private String firstName;
    private String lastName;

    public UserPayload(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstname() {
        return this.firstName;
    }

    public String getLastname() {
        return this.lastName;
    }

}
