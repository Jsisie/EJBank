package com.ejbank.service;

import com.ejbank.payload.UserPayload;

import javax.ejb.Local;

@Local
public interface TestBeanService {

    String test();
}
