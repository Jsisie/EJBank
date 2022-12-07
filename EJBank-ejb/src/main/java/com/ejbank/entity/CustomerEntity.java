package com.ejbank.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ejbank_customer")
@DiscriminatorValue(value = "customer")
public class CustomerEntity extends UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToMany(mappedBy = "customer")
    private List<AccountEntity> accounts;
    @ManyToOne
    @JoinColumn(name = "advisor_id", nullable = false)
    private AdvisorEntity advisorId;

    public Integer getId() {
        return id;
    }

    public AdvisorEntity getAdvisorId() {
        return advisorId;
    }

    public List<AccountEntity> getAccounts() {
        return accounts;
    }
}
