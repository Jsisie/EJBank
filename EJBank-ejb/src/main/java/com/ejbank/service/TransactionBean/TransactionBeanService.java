package com.ejbank.service.TransactionBean;

import com.ejbank.payload.TransactionListPayload;
import com.ejbank.payload.TransactionPayload;
import com.ejbank.payload.TransactionResponsePayLoad;

import javax.ejb.Local;

@Local
public interface TransactionBeanService {
    String test();

    TransactionListPayload getTransactionList(Integer accountID, Integer offset, Integer userID);

    Integer getNbTransactions(Integer userID);

    TransactionResponsePayLoad getTransactionPreview(Integer sourceID, Integer destinationID, Float amount, String author);

    TransactionResponsePayLoad getTransactionApply(Integer sourceID, Integer destinationID, Float amount, String author);

    TransactionResponsePayLoad getTransactionValidation(Integer transactionID, Boolean approve, String author);
}
