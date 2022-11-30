package com.ejbank.entity;

import javax.persistence.*;

@Entity
@Table(name = "ejbank_account_type")
public class AccountTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
