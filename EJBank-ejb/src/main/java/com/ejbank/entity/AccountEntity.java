package com.ejbank.entity;

import javax.persistence.*;

@Entity
@Table(name = "ejbank_account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
