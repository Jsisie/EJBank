package com.ejbank.service.TransactionBean;

import com.ejbank.payload.ListTransactionPayload;
import com.ejbank.payload.TransactionPayload;
import com.ejbank.payload.TransactionRequestPayload;
import com.ejbank.payload.TransactionResponsePayLoad;

import javax.ejb.Local;

@Local
public interface TransactionBeanService {

    ListTransactionPayload getTransactionList(Integer accountID, Integer offset, Integer userID);

    Integer getNbTransactions(Integer userID);

    TransactionResponsePayLoad getTransactionPreview(TransactionRequestPayload transactionPayload);

    TransactionResponsePayLoad getTransactionApply(TransactionRequestPayload transactionPayload);

    TransactionResponsePayLoad getTransactionValidation(TransactionRequestPayload transactionPayload);
}
