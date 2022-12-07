package com.ejbank.repository;

import com.ejbank.entity.AccountEntity;
import com.ejbank.entity.UserEntity;
import com.ejbank.payload.TransactionListPayload;
import com.ejbank.payload.TransactionPayload;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@Stateless
@LocalBean
public class TransactionRepository {

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    public TransactionListPayload getTransactionList(Integer accountID, Integer offset, Integer userID) {
        return null;
    }

    public Integer getNbTransactions(Integer userID) {
        var user = em.find(UserEntity.class, userID);
        return user.getTransactions().stream().filter(transaction -> !transaction.getApplied()).toList().size();
    }
}
