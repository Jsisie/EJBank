package com.ejbank.repository;

import com.ejbank.entity.UserEntity;
import com.ejbank.payload.UserPayload;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UserRepository {

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    /**
     *
     * @param id
     * @return
     */
    public UserPayload getName(int id) {
        var user = em.find(UserEntity.class, id);
        return new UserPayload(user.getFirstname(), user.getLastname());
    }
}
