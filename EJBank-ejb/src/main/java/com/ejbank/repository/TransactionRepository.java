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
        System.out.println("accountID = " + accountID + ", offset = " + offset + ", userID = " + userID);
        var user = em.find(UserEntity.class, userID);

        // TODO - returns null
        var account = em.find(AccountEntity.class, accountID);

        System.out.println(account);
        if(account != null)
            System.out.println("Balance = " + account.getBalance());

//        var transactions = user.getTransactions();
//        var transactions = account.getTransactionsFrom();

        var transactionsList = new ArrayList<TransactionPayload>();
        transactionsList.add(new TransactionPayload(
                "source",
                666.66f,
                user,
                user
        ));
//        for(var transaction : transactions)
//            transactionsList.add(new TransactionPayload(
//                    transaction.getId(),
//                    transaction.getDate(),
//                    transaction.getAccountFrom().getAccountType().getName(),
//                    transaction.getAccountTo().getAccountType().getName(),
//                    transaction.getAccountFrom().getCustomer().getAdvisorId().getUser(),
//                    transaction.getAuthor(),
//                    transaction.getComment(), // (TODO - fix this (??))
//                    TransactionPayload.State.TO_APPROVE, // TODO - fix this
//                    transaction.getAmount()
//            ));
        return new TransactionListPayload(transactionsList.size(), transactionsList);
    }
}
