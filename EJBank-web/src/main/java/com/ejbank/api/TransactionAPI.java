package com.ejbank.api;

import com.ejbank.payload.ListTransactionPayload;
import com.ejbank.payload.TransactionPayload;
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

    @GET
    @Path("/test")
    public String testEJBFirstName() {
        return transactionBeanService.test();
    }

    @GET
    @Path("/validation/notification/{user_id}")
    public Integer getNbTransactionNotificationFromUserId(@PathParam("user_id") Integer userID) {
        return transactionBeanService.getNbTransactions(userID);
    }

    @GET
    @Path("/list/{account_id}/{offset}/{user_id}")
    public ListTransactionPayload getAllTransactionsFromAccountFromUserID(@PathParam("account_id") Integer accountID,
                                                                          @PathParam("offset") Integer offset,
                                                                          @PathParam("user_id") Integer userID) {
        return transactionBeanService.getTransactionList(accountID, offset, userID);
    }

    @POST
    @Path("preview")
    public TransactionResponsePayLoad previewNewTransaction(TransactionPayload transactionPayload) {
        return transactionBeanService.getTransactionPreview(transactionPayload);
    }

    @POST
    @Path("apply")
    @Consumes("application/json")
    public TransactionResponsePayLoad applyNewTransaction(TransactionPayload transactionPayload) {
        return transactionBeanService.getTransactionApply(transactionPayload);
    }

    @POST
    @Path("validation")
    @Consumes("application/json")
    public TransactionResponsePayLoad validationNewTransaction(TransactionPayload transactionPayload) {
        return transactionBeanService.getTransactionValidation(transactionPayload);
    }
}
