package com.ejbank.service;

import com.ejbank.entity.UserEntity;
import com.ejbank.repository.UserRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class TestBeanServiceImpl implements TestBeanService {

    @Inject
    private UserRepository userRepository;

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    public TestBeanServiceImpl() {
    }

    @Override
    public String test() {
        return "Hello from EJB";
    }

    @Override
    public String getFirstName(int id) {
        var user = em.find(UserEntity.class, id);
//        return user.firstName();
        return "robin";
    }
}
