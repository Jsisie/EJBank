package com.ejbank.service;

import javax.ejb.Local;

@Local
public interface TestBeanService {

    String test();
    String getFirstName(int id);
}
