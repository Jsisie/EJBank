package com.ejbank.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ejbank_advisor")
@DiscriminatorValue(value = "advisor")
public class AdvisorEntity extends UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy = "advisorId")
    private List<CustomerEntity> customers;

    public Integer getId() {
        return id;
    }

    public List<CustomerEntity> getCustomers() {
        return customers;
    }
}
