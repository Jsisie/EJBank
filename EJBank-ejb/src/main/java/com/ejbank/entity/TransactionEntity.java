package com.ejbank.entity;

import javax.persistence.*;

@Entity
@Table(name = "ejbank_user")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
