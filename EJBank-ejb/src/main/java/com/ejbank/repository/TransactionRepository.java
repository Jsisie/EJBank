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
import java.util.Optional;

@Stateless
public class TransactionRepository {
    @EJB
    private RepositoryUtilsLocal utils;
    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    /**
     * @param userID
     * @return
     */
    public Integer getNbTransactions(Integer userID) {
        var user = em.find(UserEntity.class, userID);
        if (!utils.isAdvisor(user))
            return 0;
        var advisor = em.find(AdvisorEntity.class, userID);
        var customers = advisor.getCustomers();
        int nbTransactionNotApplied = 0;
        for (var customer : customers) {
            nbTransactionNotApplied += customer.getAccounts().stream()
                    .map(accountEntity -> accountEntity.getTransactionsFrom().stream()
                            .filter(transaction -> !transaction.getApplied())
                            .count())
                    .reduce(0L, Long::sum);
        }
        return nbTransactionNotApplied;
    }

    /**
     * @param accountID
     * @param offset
     * @param userID
     * @return
     */
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

    /**
     * @param transactionPayload
     * @return
     */
    public TransactionResponsePayLoad getTransactionPreview(TransactionRequestPayload transactionPayload) {
        var sourceAccount = em.find(AccountEntity.class, transactionPayload.getSource());
        var destinationAccount = em.find(AccountEntity.class, transactionPayload.getDestination());
        if (sourceAccount == null)
            return new TransactionResponsePayLoad("The source ID given does not correspond to your account");
        if (destinationAccount == null)
            return new TransactionResponsePayLoad("The destination ID given does not correspond to any destination account");
        return sourceAccount.getBalance() + sourceAccount.getAccountType().getOverdraft() >= transactionPayload.getAmount() ?
                new TransactionResponsePayLoad(
                        true,
                        sourceAccount.getBalance() - transactionPayload.getAmount(),
                        destinationAccount.getBalance() + transactionPayload.getAmount(),
                        "Vous  disposez d'un solde suffisant") :
                new TransactionResponsePayLoad(
                        false,
                        sourceAccount.getBalance(),
                        destinationAccount.getBalance(),
                        "Vous ne disposez pas d'un solde suffisant...");
    }

    /**
     * Create a new transaction that can either be validated or not
     *
     * @param transactionPayload
     * @return
     */
    public TransactionResponsePayLoad getTransactionApply(TransactionPayload transactionPayload) {

        return null;
    }

    /**
     * Validate a transaction for an Advisor
     *
     * @param transactionPayload
     * @return
     */
    public TransactionResponsePayLoad getTransactionValidation(TransactionRequestPayload transactionPayload) {
//        EntityTransaction tx = em.getTransaction();
//        try {
//            tx.begin();
        UserEntity user = em.find(UserEntity.class, transactionPayload.getAuthor());
        TransactionEntity transaction = em.find(TransactionEntity.class, transactionPayload.getTransaction());
        if (transaction == null)
            return new TransactionResponsePayLoad("The transaction ID given does not correspond to any transaction");

        var account = transaction.getAccountFrom();
        if (account == null)
            throw new IllegalStateException();

        if (!utils.isAdvisor(user))
            return new TransactionResponsePayLoad("User is not an advisor");

        Optional<String> returnError = utils.isAccountReattachedToUser(account.getId(), user.getId(), user);
        if (returnError.isPresent())
            return new TransactionResponsePayLoad(returnError.get());

        if (!transactionPayload.getApprove()) {
            em.createQuery("UPDATE TransactionEntity t SET t.applied = " + false + " WHERE t.id = :transactionId")
                    .setParameter("transactionId", transaction.getId())
                    .executeUpdate();
            return new TransactionResponsePayLoad(false, "Transaction canceled.");
        }

        if (account.getBalance() + account.getAccountType().getOverdraft() <= transaction.getAmount())
            return new TransactionResponsePayLoad(false, "Balance too low, transaction was not applied.");

        em.createQuery("UPDATE TransactionEntity t SET t.applied = " + true + " WHERE t.id = :transactionId")
                .setParameter("transactionId", transaction.getId())
                .executeUpdate();

        em.createQuery("UPDATE AccountEntity a SET a.balance = a.balance - :amount WHERE a.id = :accountId")
                .setParameter("amount", transaction.getAmount())
                .setParameter("accountId", account.getId())
                .executeUpdate();
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            return new TransactionResponsePayLoad("An error occurred and the transaction could not be applied");
//        }
        return new TransactionResponsePayLoad(true, "Transaction applied.");
    }
}
