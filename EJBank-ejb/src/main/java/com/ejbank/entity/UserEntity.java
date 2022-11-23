package com.ejbank.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ejbank_user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "firstName", nullable =false, length = 20)
    private String firstName;
    @Column(name = "lastName", nullable =false, length = 20)
    private String lastName;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    public UserEntity() {}

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
