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
    private Integer Transaction;
    private String comment;
    private State state;
    private Boolean approve;
    public static enum State {
        APPLIED, TO_APPROVE, WAITING_APPROVE
    }

    public TransactionRequestPayload() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
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

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Boolean getApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    public Integer getTransaction() {
        return Transaction;
    }

    public void setTransaction(Integer transaction) {
        Transaction = transaction;
    }
}
