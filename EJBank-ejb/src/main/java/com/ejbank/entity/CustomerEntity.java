package com.ejbank.entity;

import javax.persistence.*;

@Entity
@Table(name = "ejbank_customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
