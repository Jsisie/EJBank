package com.ejbank.repository;

import com.ejbank.entity.AccountEntity;
import com.ejbank.entity.UserEntity;
import com.ejbank.payload.ListTransactionPayload;
import com.ejbank.payload.TransactionPayload;
import com.ejbank.payload.TransactionRequestPayload;
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

    public Integer getNbTransactions(Integer userID) {
        var user = em.find(UserEntity.class, userID);
        return user.getTransactions().stream().filter(transaction -> !transaction.getApplied()).toList().size();
    }

    public ListTransactionPayload getTransactionList(Integer accountID, Integer offset, Integer userID) {
        System.out.println("accountID = " + accountID + ", offset = " + offset + ", userID = " + userID); // TODO - remove sysout

        var user = em.find(UserEntity.class, userID);
        if (Utils.isAdvisor(user)) {

        }
        // TODO - CreateQuery here

        var transactionsList = new ArrayList<TransactionPayload>();
        transactionsList.add(new TransactionPayload());
        return new ListTransactionPayload(transactionsList.size(), transactionsList);
    }

    public TransactionResponsePayLoad getTransactionPreview(TransactionRequestPayload transactionPayload) {
        var sourceAccount = em.find(AccountEntity.class, transactionPayload.getSource());
        var destinationAccount = em.find(AccountEntity.class, transactionPayload.getDestination());
        return sourceAccount.getBalance() >= transactionPayload.getAmount() ?
                new TransactionResponsePayLoad(true, sourceAccount.getBalance() - transactionPayload.getAmount(),
                        destinationAccount.getBalance() + transactionPayload.getAmount(), "Vous  disposez d'un solde suffisant")
                : new TransactionResponsePayLoad(false, sourceAccount.getBalance(),
                destinationAccount.getBalance(), "Vous ne disposez pas d'un solde suffisant...");
    }

    public TransactionResponsePayLoad getTransactionApply(TransactionPayload transactionPayload) {
        // TODO
        return null;
    }

    public TransactionResponsePayLoad getTransactionValidation(TransactionPayload transactionPayload) {
        // TODO
        return null;
    }
}
