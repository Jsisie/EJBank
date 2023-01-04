package com.ejbank.payload;

import com.ejbank.entity.UserEntity;

import java.util.Date;

public class TransactionRequestPayload {
    private int id;
    private Date date;
    private Integer source;
    private Integer destination;
    private UserEntity user;
    private Float amount;
    private Integer author;
    private String comment;
    private State state;

    public TransactionRequestPayload(){}

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Integer getSource() {
        return source;
    }

    public Integer getDestination() {
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

    public Integer getAuthor() {
        //return author.getFirstname() + " " + author.getLastname();
        return author;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setState(State state) {
        this.state = state;
    }
}
