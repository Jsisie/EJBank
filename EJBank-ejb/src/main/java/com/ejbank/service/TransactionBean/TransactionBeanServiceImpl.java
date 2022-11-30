package com.ejbank.service.TransactionBean;

import com.ejbank.repository.AccountRepository;
import com.ejbank.repository.TransactionRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class TransactionBeanServiceImpl implements TransactionBeanService {

    @Inject
    private TransactionRepository transactionRepository;

    public TransactionBeanServiceImpl() {
    }

    @Override
    public String test() {
        return "test Transaction";
    }
}
