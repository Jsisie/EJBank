package com.ejbank.service.AccountBean;

import com.ejbank.payload.ListAccountsPayload;
import com.ejbank.payload.UserPayload;

import javax.ejb.Local;

@Local
public interface AccountBeanService {
    String test();

    ListAccountsPayload getAccountsFromUserId(Integer id);
}
