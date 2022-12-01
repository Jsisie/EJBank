package com.ejbank.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ejbank_customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToMany(mappedBy = "customer")
    private List<AccountEntity> accounts;
    @ManyToOne
    @JoinColumn(name = "advisor_id", nullable = false)
    private AdvisorEntity advisorId;
    @ManyToOne
    private UserEntity user;

    public int getId() {
        return id;
    }

    public AdvisorEntity getAdvisorId() {
        return advisorId;
    }

    public List<AccountEntity> getAccounts() {
        return accounts;
    }

    public UserEntity getUser() {
        return user;
    }
}
