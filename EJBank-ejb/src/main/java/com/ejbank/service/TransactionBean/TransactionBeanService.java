package com.ejbank.service.TransactionBean;

import com.ejbank.payload.ListTransactionPayload;
import com.ejbank.payload.TransactionPayload;
import com.ejbank.payload.TransactionResponsePayLoad;

import javax.ejb.Local;

@Local
public interface TransactionBeanService {
    String test();

    ListTransactionPayload getTransactionList(Integer accountID, Integer offset, Integer userID);

    Integer getNbTransactions(Integer userID);

    TransactionResponsePayLoad getTransactionPreview(TransactionPayload transactionPayload);

    TransactionResponsePayLoad getTransactionApply(TransactionPayload transactionPayload);

    TransactionResponsePayLoad getTransactionValidation(TransactionPayload transactionPayload);
}
