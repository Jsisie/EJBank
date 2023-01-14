package com.ejbank.service.AccountBean;

import com.ejbank.payload.AccountPayload;

import javax.ejb.Local;

@Local
public interface AccountBeanService {

    AccountPayload getAccount(Integer accountID, Integer userID);
}
