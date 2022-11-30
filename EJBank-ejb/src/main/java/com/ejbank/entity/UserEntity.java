package com.ejbank.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ejbank_user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "firstname", nullable =false, length = 20)
    private String firstname;
    @Column(name = "lastname", nullable =false, length = 20)
    private String lastname;

    public UserEntity() {}

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }
}
