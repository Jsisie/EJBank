package com.ejbank.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ejbank_advisor")
public class AdvisorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToMany(mappedBy = "advisorId")
    private List<CustomerEntity> customers;
    @ManyToOne
    private UserEntity user;

    public int getId() {
        return id;
    }

    public List<CustomerEntity> getCustomers() {
        return customers;
    }

    public UserEntity getUser() {
        return user;
    }
}
