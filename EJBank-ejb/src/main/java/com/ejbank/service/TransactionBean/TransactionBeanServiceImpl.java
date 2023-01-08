package com.ejbank.service.TransactionBean;

import com.ejbank.payload.ListTransactionPayload;
import com.ejbank.payload.TransactionPayload;
import com.ejbank.payload.TransactionRequestPayload;
import com.ejbank.payload.TransactionResponsePayLoad;
import com.ejbank.repository.TransactionRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class TransactionBeanServiceImpl implements TransactionBeanService {

    @Inject
    private TransactionRepository transactionRepository;

    /**
     * TransactionBeanServiceImpl constructor.
     */
    public TransactionBeanServiceImpl() {
    }

    /**
     * Takes an advisor's ID as a parameter and returns the number of customer transactions he has yet to apply.
     * If the provided id is not an advisor's, the method returns 0.
     *
     * @param userID the ID of the advisor whose transactions will be counted. (Integer)
     * @return The number of yet-to-be-applied transactions of his customers, or 0 if userId is not an advisor's. (int)
     */
    @Override
    public Integer getNbTransactions(Integer userID) {
        return transactionRepository.getNbTransactions(userID);
    }

    /**
     * Returns a list of transactions going to and from a customer's account from a given offset (ids).
     *
     * @param accountID The account from which the transactions are going to get retrieved from. (Integer)
     * @param offset  Delays the number of the first transaction that is retrieved. (Integer)
     * @param userID The user id used to check if the specified account belongs to it. (Integer)
     * @return a ListTransactionPayload which contains a list of the user's transactions from a certain offset.
     */
    @Override
    public ListTransactionPayload getTransactionList(Integer accountID, Integer offset, Integer userID) {
        return transactionRepository.getTransactionList(accountID, offset, userID);
    }

    /**
     * Tells information about a transaction request, whether source balance is high enough or not,
     * or if the source ID or destination ID are not valid.
     *
     * @param transactionPayload The transaction request to check (TransactionRequestPayload)
     * @return a response based on the transaction's status. (TransactionResponsePayload)
     */
    @Override
    public TransactionResponsePayLoad getTransactionPreview(TransactionRequestPayload transactionPayload) {
        return transactionRepository.getTransactionPreview(transactionPayload);
    }

    /**
     * Create a new transaction that can either be validated or not
     *
     * @param transactionPayload the request for a transaction to apply (TransactionRequestPayload)
     * @return a transaction response after applying (substracting an amount in an account and putting it in another)
     * (or not) the transaction. (TransactionResponsePayLoad)
     */
    @Override
    public TransactionResponsePayLoad getTransactionApply(TransactionRequestPayload transactionPayload) {
        return transactionRepository.getTransactionApply(transactionPayload);
    }

    /**
     * Validate a transaction for an Advisor. A transaction has to be validated by an advisor
     * if the amount transferred is superior to 1000€.
     *
     * @param transactionPayload a Transaction request that has to be validated by an advisor
     * @return A transaction response after validating or not (and then applying or not) the requested transaction
     * (TransactionResponsePayLoad)
     */
    @Override
    public TransactionResponsePayLoad getTransactionValidation(TransactionRequestPayload transactionPayload) {
        return transactionRepository.getTransactionValidation(transactionPayload);
    }


}
