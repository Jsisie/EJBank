package com.ejbank.repository;

import com.ejbank.entity.UserEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserRepository {

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    public String getFirstName(int id) {
        var user = em.find(UserEntity.class, id);
        return user.getFirstName();
    }
}
