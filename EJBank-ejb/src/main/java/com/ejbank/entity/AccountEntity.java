package com.ejbank.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ejbank_account")
public class AccountEntity {
    @OneToMany(mappedBy = "accountFrom")
    private List<TransactionEntity> transactionsFrom;
    @OneToMany(mappedBy = "accountTo")
    private List<TransactionEntity> transactionsTo;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType. EAGER)
    @JoinColumn(name = "account_type_id", nullable = false)
    private AccountTypeEntity accountType;
    @Column(name = "balance", nullable = true)
    private Float balance;

    public int getId() {
        return id;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public AccountTypeEntity getAccountType() {
        return accountType;
    }

    public Float getBalance() {
        return balance;
    }

    public List<TransactionEntity> getTransactionsFrom() {
        return transactionsFrom;
    }

    public List<TransactionEntity> getTransactionsTo() {
        return transactionsTo;
    }

}
