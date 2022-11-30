package com.ejbank.service;

import com.ejbank.payload.UserPayload;
import com.ejbank.repository.UserRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class TestBeanServiceImpl implements TestBeanService {

    @Inject
    private UserRepository userRepository;

    public TestBeanServiceImpl() {
    }

    @Override
    public String test() {
        return "Hello from EJB";
    }

    @Override
    public UserPayload getName(int id) {
        return userRepository.getName(id);
    }
}
