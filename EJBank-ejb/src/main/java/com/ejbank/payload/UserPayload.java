package com.ejbank.payload;

public class UserPayload {
    private String firstName;
    private String lastName;
    private Integer age;

    public UserPayload() {
    }

    public UserPayload(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserPayload(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstname() {
        return this.firstName;
    }

    public String getLastname() {
        return this.lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
