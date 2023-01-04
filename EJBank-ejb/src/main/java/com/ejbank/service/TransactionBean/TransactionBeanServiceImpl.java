package com.ejbank.service.TransactionBean;

import com.ejbank.payload.ListTransactionPayload;
import com.ejbank.payload.TransactionPayload;
import com.ejbank.payload.TransactionRequestPayload;
import com.ejbank.payload.TransactionResponsePayLoad;
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

    @Override
    public Integer getNbTransactions(Integer userID) {
        return transactionRepository.getNbTransactions(userID);
    }

    @Override
    public ListTransactionPayload getTransactionList(Integer accountID, Integer offset, Integer userID) {
        return transactionRepository.getTransactionList(accountID, offset, userID);
    }

    @Override
    public TransactionResponsePayLoad getTransactionPreview(TransactionRequestPayload transactionPayload) {
        return transactionRepository.getTransactionPreview(transactionPayload);
    }

    @Override
    public TransactionResponsePayLoad getTransactionApply(TransactionPayload transactionPayload) {
        return transactionRepository.getTransactionApply(transactionPayload);
    }

    @Override
    public TransactionResponsePayLoad getTransactionValidation(TransactionPayload transactionPayload) {
        return transactionRepository.getTransactionValidation(transactionPayload);
    }


}
