package com.ejbank.payload;

import com.ejbank.entity.UserEntity;

import java.util.Date;

public class TransactionPayload {
    private int id;
    private Date date;
    private String source;
    private String destination;
    private String destination_user;
    private UserEntity user;
    private Float amount;
    private String author;
    private String comment;
    private State state;

    public static enum State {
        APPLIED, TO_APPROVE, WAITING_APPROVE
    }

    public TransactionPayload(){}

    public TransactionPayload(int id,
                              Date date,
                              String sourceName,
                              String destination,
                              String destinationUserFirstName,
                              Float amount,
                              String authorFirstName,
                              String authorLastName,
                              String comment,
                              State state) {
        this.id = id;
        this.date = date;
        this.source = sourceName;
        this.destination = destination;
        this.destination_user = destinationUserFirstName;
        this.amount = amount;
        this.author = authorFirstName + " " + authorLastName;
        this.comment = comment;
        this.state = state;
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
        return destination_user;
    }

    public UserEntity getUser() {
        return user;
    }

    public Float getAmount() {
        return amount;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public State getState() {
        return state;
    }
}
