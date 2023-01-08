package com.ejbank.api;

import com.ejbank.payload.ListTransactionPayload;
import com.ejbank.payload.TransactionPayload;
import com.ejbank.payload.TransactionRequestPayload;
import com.ejbank.payload.TransactionResponsePayLoad;
import com.ejbank.service.TransactionBean.TransactionBeanServiceImpl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class TransactionAPI {
    @EJB
    private TransactionBeanServiceImpl transactionBeanService;

    /**
     * Takes an advisor's ID as a parameter and returns the number of customer transactions he has yet to apply.
     * If the provided id is not an advisor's, the method returns 0.
     *
     * @param userID the ID of the advisor whose transactions will be counted. (Integer)
     * @return The number of yet-to-be-applied transactions of his customers, or 0 if userId is not an advisor's. (int)
     */
    @GET
    @Path("/validation/notification/{user_id}")
    public Integer getNbTransactionNotificationFromUserId(@PathParam("user_id") Integer userID) {
        return transactionBeanService.getNbTransactions(userID);
    }

    /**
     * Returns a list of transactions going to and from a customer's account from a given offset (ids).
     *
     * @param accountID The account from which the transactions are going to get retrieved from. (Integer)
     * @param offset  Delays the number of the first transaction that is retrieved. (Integer)
     * @param userID The user id used to check if the specified account belongs to it. (Integer)
     * @return a ListTransactionPayload which contains a list of the user's transactions from a certain offset.
     */
    @GET
    @Path("/list/{account_id}/{offset}/{user_id}")
    public ListTransactionPayload getAllTransactionsFromAccountFromUserID(@PathParam("account_id") Integer accountID,
                                                                          @PathParam("offset") Integer offset,
                                                                          @PathParam("user_id") Integer userID) {
        return transactionBeanService.getTransactionList(accountID, offset, userID);
    }

    /**
     * Tells information about a transaction request, whether source balance is high enough or not,
     * or if the source ID or destination ID are not valid.
     *
     * @param transactionRequestPayload The transaction request to check (TransactionRequestPayload)
     * @return a response based on the transaction's status. (TransactionResponsePayload)
     */
    @POST
    @Path("preview")
    public TransactionResponsePayLoad previewNewTransaction(TransactionRequestPayload transactionRequestPayload) {
        return transactionBeanService.getTransactionPreview(transactionRequestPayload);
    }

    /**
     * Create a new transaction that can either be validated or not
     *
     * @param transactionRequestPayload the request for a transaction to apply (TransactionRequestPayload)
     * @return a transaction response after applying (substracting an amount in an account and putting it in another)
     * (or not) the transaction. (TransactionResponsePayLoad)
     */
    @POST
    @Path("apply")
    @Consumes("application/json")
    public TransactionResponsePayLoad applyNewTransaction(TransactionRequestPayload transactionRequestPayload) {
        return transactionBeanService.getTransactionApply(transactionRequestPayload);
    }

    /**
     * Validate a transaction for an Advisor. A transaction has to be validated by an advisor
     * if the amount transferred is superior to 1000€.
     *
     * @param transactionRequestPayload a Transaction request that has to be validated by an advisor
     * @return A transaction response after validating or not (and then applying or not) the requested transaction
     * (TransactionResponsePayLoad)
     */
    @POST
    @Path("validation")
    @Consumes("application/json")
    public TransactionResponsePayLoad validationNewTransaction(TransactionRequestPayload transactionRequestPayload) {
        return transactionBeanService.getTransactionValidation(transactionRequestPayload);
    }
}
