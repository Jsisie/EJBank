package com.ejbank.service.AccountsBean;

import com.ejbank.payload.AccountPayload;
import com.ejbank.payload.ListAccountsPayload;
import com.ejbank.repository.AccountsRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class AccountsBeanServiceImpl implements AccountsBeanService {

    @Inject
    private AccountsRepository accountsRepository;

    public AccountsBeanServiceImpl() {
    }

    @Override
    public String test() {
        return "test Account";
    }

    @Override
    public ListAccountsPayload getAccounts(Integer id) {
        return accountsRepository.getAccounts(id);
    }

    @Override
    public ListAccountsPayload getAttachedAccounts(Integer id) {
        return accountsRepository.getAttachedAccounts(id);
    }

    @Override
    public ListAccountsPayload getAllAccounts(Integer id) {
        return accountsRepository.getAllAccounts(id);
    }
}
