package com.ejbank.api;

import com.ejbank.payload.TransactionListPayload;
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
    public TransactionListPayload getAllTransactionsFromAccountFromUserID(@PathParam("account_id") Integer accountID,
                                                                          @PathParam("offset") Integer offset,
                                                                          @PathParam("user_id") Integer userID) {
        return transactionBeanService.getTransactionList(accountID, offset, userID);
    }

    @POST
    @Path("preview")
    public void previewNewTransaction(TransactionPayload transactionPayload) {
        System.out.println("source: " + transactionPayload.getSource());
        System.out.println("destination: " + transactionPayload.getDestination());
        System.out.println("amount: " + transactionPayload.getAmount());
        System.out.println("author: " + transactionPayload.getAuthor());
        //return transactionBeanService.
    }

    @POST
    @Path("apply")
    public void applyNewTransaction() {

    }

    @POST
    @Path("validation")
    public void validationNewTransaction(@QueryParam("transaction") Integer transactionID,
                                         @QueryParam("approve") Boolean decision,
                                         @QueryParam("author") Integer userID) {
        System.out.println("transaction: " + transactionID);
        System.out.println("approve: " + decision);
        System.out.println("author: " + userID);
    }
}
