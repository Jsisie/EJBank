package com.ejbank.service.AccountsBean;

import com.ejbank.payload.AccountPayload;
import com.ejbank.payload.ListAccountsPayload;

import javax.ejb.Local;

@Local
public interface AccountsBeanService {
    String test();

    ListAccountsPayload getAccounts(Integer id);

    ListAccountsPayload getAttachedAccounts(Integer id);

    ListAccountsPayload getAllAccounts(Integer id);
}
