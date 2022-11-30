package com.ejbank.payload;

import java.util.Date;

public class TransactionPayload {
    private int id;
    private Date date;
    private String source;
    private String label;
    private String destinationUser;
    private Float amount;
    private String author;
    private String comment;
    private State state;

    public TransactionPayload(int id, Date date, String source, String label,
                              String destinationUserFirstName, String destinationUserLastName,
                              String authorFirstName, String authorLastName,
                              String comment, State state, Float amount) {
        this.id = id;
        this.date = date;
        this.source = source;
        this.label = label;
        this.destinationUser = destinationUserFirstName + " " + destinationUserLastName;
        this.author = authorFirstName + " " + authorLastName;
        this.comment = comment;
        this.state = state;
        this.amount = amount;
    }

    public TransactionPayload(String source, Float amount,
                              String destinationUserFirstName, String destinationUserLastName,
                              String authorFirstName, String authorLastName) {
        this.source = source;
        this.amount = amount;
        this.destinationUser = destinationUserFirstName + " " + destinationUserLastName;
        this.author = authorFirstName + " " + authorLastName;
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

    public String getLabel() {
        return label;
    }

    public String getDestination_user() {
        return destinationUser;
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

    private enum State {
        APPLIED, TO_APPROVE, WAITING_APPROVE
    }
}
