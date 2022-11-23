package com.ejbank.test;

import javax.ejb.Local;

@Local
public interface TestBeanLocal {

    String test();
}
