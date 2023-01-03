package com.ejbank.service.AccountBean;

import com.ejbank.payload.AccountPayload;
import com.ejbank.payload.ListAccountsPayload;

import javax.ejb.Local;

@Local
public interface AccountBeanService {
    String test();

    AccountPayload getAccount(Integer accountID, Integer userID);
}
