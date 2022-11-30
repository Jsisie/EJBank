package com.ejbank.service;

import com.ejbank.payload.UserPayload;
import com.ejbank.repository.UserRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class TestBeanServiceImpl implements TestBeanService {

    public TestBeanServiceImpl() {
    }

    @Override
    public String test() {
        return "Hello from EJB";
    }
}
