package com.ejbank.service.TransactionBean;

import com.ejbank.payload.TransactionListPayload;
import com.ejbank.payload.TransactionPayload;

import javax.ejb.Local;

@Local
public interface TransactionBeanService {
    String test();

    TransactionListPayload getTransactionList(Integer accountID, Integer offset, Integer userID);
}
