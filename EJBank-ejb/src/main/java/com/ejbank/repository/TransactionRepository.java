package com.ejbank.repository;

import com.ejbank.entity.AccountEntity;
import com.ejbank.entity.UserEntity;
import com.ejbank.payload.TransactionListPayload;
import com.ejbank.payload.TransactionPayload;
import com.ejbank.payload.TransactionResponsePayLoad;

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

    public TransactionResponsePayLoad getTransactionPreview(TransactionPayload transactionPayload) {

        var account = em.find(AccountEntity.class, transactionPayload.getSource());
        var destination = em.find(AccountEntity.class, transactionPayload.getDestination());
        return account.getBalance() > transactionPayload.getAmount()?
                new TransactionResponsePayLoad(true, account.getBalance() - transactionPayload.getAmount(), destination.getBalance() + transactionPayload.getAmount(), "Vous  disposez d'un solde suffisant"):
                new TransactionResponsePayLoad(false, account.getBalance() - transactionPayload.getAmount(), destination.getBalance() + transactionPayload.getAmount(), "Vous ne disposez pas d'un solde suffisant...");
    }

    public TransactionResponsePayLoad getTransactionApply(Integer sourceID, Integer destinationID, Float amount, String author) {
        // TODO
        return null;
    }

    public TransactionResponsePayLoad getTransactionValidation(Integer transactionID, Boolean approve, String author) {
        // TODO
        return null;
    }
}
