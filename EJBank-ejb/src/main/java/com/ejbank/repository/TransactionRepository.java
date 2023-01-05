package com.ejbank.repository;

import com.ejbank.entity.AccountEntity;
import com.ejbank.entity.AdvisorEntity;
import com.ejbank.entity.TransactionEntity;
import com.ejbank.entity.UserEntity;
import com.ejbank.payload.ListTransactionPayload;
import com.ejbank.payload.TransactionPayload;
import com.ejbank.payload.TransactionRequestPayload;
import com.ejbank.payload.TransactionResponsePayLoad;
import com.ejbank.repository.utils.RepositoryUtilsLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class TransactionRepository {
    @EJB
    private RepositoryUtilsLocal utils;

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    public Integer getNbTransactions(Integer userID) {
        var user = em.find(UserEntity.class, userID);
        if(!utils.isAdvisor(user))
            return 0;
        return user.getTransactions().stream().filter(transaction -> !transaction.getApplied()).toList().size();
    }

    public ListTransactionPayload getTransactionList(Integer accountID, Integer offset, Integer userID) {
        var user = em.find(UserEntity.class, userID);

        var returnError = utils.isAccountReattachedToUser(accountID, userID, user);
        if (returnError.isPresent())
            return new ListTransactionPayload(returnError.get());

        int MAX_RESULTS = 5;
        Query query = em.createQuery(
                "SELECT t FROM TransactionEntity t " +
                        "WHERE t.accountFrom.id = " + accountID + " " +
                        "ORDER BY t.date DESC"
        );
        query.setFirstResult(offset);
        query.setMaxResults(MAX_RESULTS);
        List<TransactionEntity> transactions = query.getResultList();

        query = em.createQuery("SELECT COUNT(t) FROM TransactionEntity t WHERE t.accountFrom.id = " + accountID);
        long total = (Long) query.getSingleResult();

        var transactionsList = new ArrayList<TransactionPayload>();
        transactions.forEach(transaction -> {
            TransactionPayload.State state = transaction.getApplied() ?
                    TransactionPayload.State.APPLIED :
                    (user instanceof AdvisorEntity) ?
                            TransactionPayload.State.TO_APPROVE :
                            TransactionPayload.State.WAITING_APPROVE;

            transactionsList.add(new TransactionPayload(
                    transaction.getId(),
                    transaction.getDate(),
                    transaction.getAccountFrom().getAccountType().getName(),
                    transaction.getAccountTo().getAccountType().getName(),
                    transaction.getAccountTo().getCustomer().getFirstname(),
                    transaction.getAmount(),
                    transaction.getAccountFrom().getCustomer().getFirstname(),
                    transaction.getAccountFrom().getCustomer().getLastname(),
                    transaction.getComment(),
                    state
            ));
        });
        return new ListTransactionPayload(total, transactionsList);
    }

    public TransactionResponsePayLoad getTransactionPreview(TransactionRequestPayload transactionPayload) {
        var sourceAccount = em.find(AccountEntity.class, transactionPayload.getSource());
        var destinationAccount = em.find(AccountEntity.class, transactionPayload.getDestination());
        return sourceAccount.getBalance() + sourceAccount.getAccountType().getOverdraft() >= transactionPayload.getAmount() ?
                new TransactionResponsePayLoad(true, sourceAccount.getBalance() - transactionPayload.getAmount(),
                        destinationAccount.getBalance() + transactionPayload.getAmount(), "Vous  disposez d'un solde suffisant")
                : new TransactionResponsePayLoad(false, sourceAccount.getBalance(),
                destinationAccount.getBalance(), "Vous ne disposez pas d'un solde suffisant...");
    }

    public TransactionResponsePayLoad getTransactionApply(TransactionPayload transactionPayload) {
        // TODO - return TransactionResponsePayload
        return null;
    }

    public TransactionResponsePayLoad getTransactionValidation(TransactionPayload transactionPayload) {
        // TODO - return TransactionResponsePayload
        return null;
    }
}
