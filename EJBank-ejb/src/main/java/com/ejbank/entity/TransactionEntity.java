package com.ejbank.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ejbank_transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "account_id_from", nullable = false)
    private AccountEntity accountFrom;
    @ManyToOne
    @JoinColumn(name = "account_id_to", nullable = false)
    private AccountEntity accountTo;
    @ManyToOne
    @JoinColumn(name = "author", nullable = true)
    private UserEntity author;
    @Column(name = "amount", nullable = false)
    private Float amount;
    @Column(name = "comment", nullable = true, length = 255)
    private String comment;
    @Column(name = "applied", nullable = false)
    private Boolean applied;
    @Column(name = "date", nullable = false)
    private Date date;

    public TransactionEntity() {
    }

    public int getId() {
        return id;
    }

    public AccountEntity getAccountFrom() {
        return accountFrom;
    }

    public AccountEntity getAccountTo() {
        return accountTo;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public Float getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public Boolean getApplied() {
        return applied;
    }

    public Date getDate() {
        return date;
    }
}
