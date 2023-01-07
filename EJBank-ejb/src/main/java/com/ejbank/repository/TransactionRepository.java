package com.ejbank.repository;

import com.ejbank.entity.*;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Stateless
public class TransactionRepository {
    @EJB
    private RepositoryUtilsLocal utils;
    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    /**
     * Takes an advisor's ID as a parameter and returns the number of customer transactions he has yet to apply.
     * If the provided id is not an advisor's, the method returns 0.
     *
     * @param userID the ID of the advisor whose transactions will be counted. (Integer)
     * @return The number of yet-to-be-applied transactions of his customers, or 0 if userId is not an advisor's. (int)
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
     * Returns a list of transactions going to and from a customer's account from a given offset (ids).
     *
     * @param accountID The account from which the transactions are going to get retrieved from. (Integer)
     * @param offset  Delays the number of the first transaction that is retrieved. (Integer)
     * @param userID The user id used to check if the specified account belongs to it. (Integer)
     * @return a ListTransactionPayload which contains a list of the user's transactions from a certain offset.
     */
    public ListTransactionPayload getTransactionList(Integer accountID, Integer offset, Integer userID) {
        var user = em.find(UserEntity.class, userID);

        var returnError = utils.isAccountReattachedToUser(accountID, userID, user);
        if (returnError.isPresent())
            return new ListTransactionPayload(returnError.get());

        int MAX_RESULTS = 5;
        Query query = em.createQuery(
                "SELECT t FROM TransactionEntity t " +
                        "WHERE (t.accountFrom.id = " + accountID + " " +
                        "OR t.accountTo.id = " + accountID + " ) " +
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
     * Tells information about a transaction request, whether source balance is high enough or not,
     * or if the source ID or destination ID are not valid.
     *
     * @param transactionPayload The transaction request to check (TransactionRequestPayload)
     * @return a response based on the transaction's status. (TransactionResponsePayload)
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
     * @param transactionPayload the request for a transaction to apply (TransactionRequestPayload)
     * @return a transaction response after applying (substracting an amount in an account and putting it in another)
     * (or not) the transaction. (TransactionResponsePayLoad)
     */
    public TransactionResponsePayLoad getTransactionApply(TransactionRequestPayload transactionPayload) {
        var sourceAccount = em.find(AccountEntity.class, transactionPayload.getSource());
        var destinationAccount = em.find(AccountEntity.class, transactionPayload.getDestination());
        var user = em.find(UserEntity.class, transactionPayload.getAuthor());
        var amount = transactionPayload.getAmount();

        if (sourceAccount == null)
            return new TransactionResponsePayLoad("The source ID given does not correspond to your account");
        if (destinationAccount == null)
            return new TransactionResponsePayLoad("The destination ID given does not correspond to any destination account");
        if (user == null)
            return new TransactionResponsePayLoad("The source ID given does not correspond to any user");

        if (amount < 0)
            return new TransactionResponsePayLoad("Amount must be superior than 0");

        if (utils.isAdvisor(user)) {
            var advisor = (AdvisorEntity) user;
            var customers = advisor.getCustomers();
            if (!customers.contains(sourceAccount.getCustomer()) || !customers.contains(destinationAccount.getCustomer()))
                return new TransactionResponsePayLoad("You're not allowed to perform a transaction with an account that does not belong to any of your customer");
        } else {
            var customer = (CustomerEntity) user;
            if (sourceAccount.getCustomer() != customer || destinationAccount.getCustomer() != customer)
                return new TransactionResponsePayLoad("You're not allowed to perform a transaction with an account that does not belong to you");
        }

        if (sourceAccount.getBalance() + sourceAccount.getAccountType().getOverdraft() < transactionPayload.getAmount())
            return new TransactionResponsePayLoad("The amount of the transaction is too high for your account");

        var beforeBalance = sourceAccount.getBalance();
        var comment = transactionPayload.getComment();
        if(comment.length() > 255)
            return new TransactionResponsePayLoad("Your comment must be lower than 255 characters");

        var transaction = new TransactionEntity();
        transaction.setAmount(amount);
        transaction.setAccountFrom(sourceAccount);
        transaction.setAccountTo(destinationAccount);
        transaction.setAuthor(user);
        transaction.setComment(comment);
        transaction.setDate(new Date());

        if (!utils.isAdvisor(user) && amount > 1000) {
            transaction.setApplied(false);
            em.persist(transaction);
            return new TransactionResponsePayLoad(true, beforeBalance, beforeBalance,
                    "Transaction created, an Advisor will have to validate the transaction because the amount is superior to 1000 euros");
        }

        transaction.setApplied(true);
        em.persist(transaction);

        // Subtract the amount value to the source balance
        em.createQuery("UPDATE AccountEntity a SET a.balance = a.balance - :amount WHERE a.id = :accountId")
                .setParameter("amount", transaction.getAmount())
                .setParameter("accountId", sourceAccount.getId())
                .executeUpdate();

        // Add the amount value to the destination balance
        em.createQuery("UPDATE AccountEntity a SET a.balance = a.balance + :amount WHERE a.id = :accountId")
                .setParameter("amount", transaction.getAmount())
                .setParameter("accountId", destinationAccount.getId())
                .executeUpdate();

        return new TransactionResponsePayLoad(true, beforeBalance, beforeBalance - amount, "Transaction create and applied");
    }

    /**
     * Validate a transaction for an Advisor. A transaction has to be validated by an advisor
     * if the amount transferred is superior to 1000€.
     *
     * @param transactionPayload a Transaction request that has to be validated by an advisor
     * @return A transaction response after validating or not (and then applying or not) the requested transaction
     * (TransactionResponsePayLoad)
     */
    public TransactionResponsePayLoad getTransactionValidation(TransactionRequestPayload transactionPayload) {
        UserEntity user = em.find(UserEntity.class, transactionPayload.getAuthor());
        TransactionEntity transaction = em.find(TransactionEntity.class, transactionPayload.getTransaction());
        if (transaction == null)
            return new TransactionResponsePayLoad("The transaction ID given does not correspond to any transaction");

        var sourceAccount = transaction.getAccountFrom();
        var destinationAccount = transaction.getAccountTo();
        if (sourceAccount == null || destinationAccount == null)
            throw new IllegalStateException();

        if (!utils.isAdvisor(user))
            return new TransactionResponsePayLoad("User is not an advisor");

        Optional<String> returnError = utils.isAccountReattachedToUser(sourceAccount.getId(), user.getId(), user);
        if (returnError.isPresent())
            return new TransactionResponsePayLoad(returnError.get());

        if (!transactionPayload.getApprove()) {
            em.remove(transaction);
            return new TransactionResponsePayLoad(true, "Transaction canceled.");
        }

        if (sourceAccount.getBalance() + sourceAccount.getAccountType().getOverdraft() <= transaction.getAmount())
            return new TransactionResponsePayLoad(false, "Balance too low, transaction was not applied.");

        // Set the applied field to true
        em.createQuery("UPDATE TransactionEntity t SET t.applied = " + true + " WHERE t.id = :transactionId")
                .setParameter("transactionId", transaction.getId())
                .executeUpdate();

        // Subtract the amount value to the source balance
        em.createQuery("UPDATE AccountEntity a SET a.balance = a.balance - :amount WHERE a.id = :accountId")
                .setParameter("amount", transaction.getAmount())
                .setParameter("accountId", sourceAccount.getId())
                .executeUpdate();

        // Add the amount value to the destination balance
        em.createQuery("UPDATE AccountEntity a SET a.balance = a.balance + :amount WHERE a.id = :accountId")
                .setParameter("amount", transaction.getAmount())
                .setParameter("accountId", destinationAccount.getId())
                .executeUpdate();

        return new TransactionResponsePayLoad(true, "Transaction applied.");
    }
}
