package com.ejbank.test;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class TestBean implements TestBeanLocal {

    @Override
    public String test() {
        return "Hello from EJB";
    }
}
