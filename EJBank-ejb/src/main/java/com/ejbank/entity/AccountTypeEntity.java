package com.ejbank.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ejbank_account_type")
public class AccountTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "rate", nullable = false)
    private Float rate;
    @Column(name = "overdraft", nullable = false)
    private int overdraft;
    @OneToMany(mappedBy = "accountType")
    private List<AccountEntity> accounts;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getRate() {
        return rate;
    }

    public int getOverdraft() {
        return overdraft;
    }

    public List<AccountEntity> getAccountsType() {
        return accounts;
    }
}
