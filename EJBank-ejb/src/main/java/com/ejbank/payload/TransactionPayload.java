package com.ejbank.payload;

import com.ejbank.entity.UserEntity;

import java.util.Date;

public class TransactionPayload {
    private int id;
    private Date date;
    private Integer source;
    private String destinationName;
    private String destinationUser;
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
                              Integer source,
                              String destinationName,
                              String destinationUserFirstName,
                              String destinationUserLastName,
                              Float amount,
                              String author,
                              String comment,
                              State state) {
        this.id = id;
        this.date = date;
        this.source = source;
        this.destinationName = destinationName;
        this.destinationUser = destinationUserFirstName + " " + destinationUserLastName;
        this.amount = amount;
        this.author = author;
        this.comment = comment;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Integer getSource() {
        return source;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public String getDestinationUser() {
        return destinationUser;
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
