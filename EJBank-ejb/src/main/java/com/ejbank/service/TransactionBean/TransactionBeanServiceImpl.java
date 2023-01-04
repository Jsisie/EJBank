package com.ejbank.service.TransactionBean;

import com.ejbank.payload.TransactionListPayload;
import com.ejbank.payload.TransactionPayload;
import com.ejbank.payload.TransactionResponsePayLoad;
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

    @Override
    public Integer getNbTransactions(Integer userID) {
        return transactionRepository.getNbTransactions(userID);
    }

    @Override
    public TransactionListPayload getTransactionList(Integer accountID, Integer offset, Integer userID) {
        return transactionRepository.getTransactionList(accountID, offset, userID);
    }

    @Override
    public TransactionResponsePayLoad getTransactionPreview(TransactionPayload transactionPayload) {
        return transactionRepository.getTransactionPreview(transactionPayload);
    }

    @Override
    public TransactionResponsePayLoad getTransactionApply(Integer sourceID, Integer destinationID, Float amount, String author) {
        return transactionRepository.getTransactionApply(sourceID, destinationID, amount, author);
    }

    @Override
    public TransactionResponsePayLoad getTransactionValidation(Integer transactionID, Boolean approve, String author) {
        return transactionRepository.getTransactionValidation(transactionID, approve, author);
    }


}
