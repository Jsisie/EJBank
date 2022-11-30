package com.ejbank.service.UserBean;

import com.ejbank.payload.UserPayload;

import javax.ejb.Local;

@Local
public interface UserBeanService {
    UserPayload getName(int id);
}
