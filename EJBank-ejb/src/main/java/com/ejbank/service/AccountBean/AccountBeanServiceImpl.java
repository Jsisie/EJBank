package com.ejbank.service.AccountBean;

import com.ejbank.payload.ListAccountsPayload;
import com.ejbank.repository.AccountRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class AccountBeanServiceImpl implements AccountBeanService {

    @Inject
    private AccountRepository accountRepository;

    public AccountBeanServiceImpl() {
    }

    @Override
    public String test() {
        return "test Account";
    }

    @Override
    public ListAccountsPayload getAccounts(Integer id) {
        return accountRepository.getAccounts(id);
    }

    @Override
    public ListAccountsPayload getAttachedAccounts(Integer id) {
        return accountRepository.getAttachedAccounts(id);
    }

    @Override
    public ListAccountsPayload getAllAccounts(Integer id) {
        return accountRepository.getAllAccounts(id);
    }
}
