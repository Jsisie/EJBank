package com.ejbank.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ejbank_user")
@DiscriminatorValue(value = "user")
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;
    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<TransactionEntity> transactions;


    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }
}
