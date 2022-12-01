package com.ejbank.payload;

import com.ejbank.entity.UserEntity;

import java.util.Date;

public class TransactionPayload {
    private int id;
    private Date date;
    private String source;
    private String destination;
    private UserEntity user;
    private Float amount;
    private UserEntity author;
    private String comment;
    private State state;

    public TransactionPayload(int id,
                              Date date,
                              String source,
                              String destination,
                              UserEntity destinationUser,
                              UserEntity author,
                              String comment,
                              State state,
                              Float amount) {
        this.id = id;
        this.date = date;
        this.source = source;
        this.destination = destination;
        this.user = destinationUser;
        this.amount = amount;
        this.author = author;
        this.comment = comment;
        this.state = state;
    }

    public TransactionPayload(String source,
                              Float amount,
                              UserEntity destinationUser,
                              UserEntity author) {
        this.source = source;
        this.amount = amount;
        this.user = destinationUser;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDestination_user() {
        return user.getFirstname() + " " + user.getLastname();
    }

    public String getSource_user() {
        return user.getFirstname() + " " + user.getLastname();
    }

    public Float getAmount() {
        return amount;
    }

    public String getAuthor() {
        return author.getFirstname() + " " + author.getLastname();
    }

    public String getComment() {
        return comment;
    }

    public State getState() {
        return state;
    }

    public static enum State {
        APPLIED, TO_APPROVE, WAITING_APPROVE
    }
}
