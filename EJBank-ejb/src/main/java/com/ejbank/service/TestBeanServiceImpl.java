package com.ejbank.service;

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
    public String getFirstName(int id) {
        return userRepository.getFirstName(id);
    }
}
