package com.ejbank.api;

import com.ejbank.payload.TransactionListPayload;
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
    @Consumes("application/json")
    public void previewNewTransaction(@FormParam("source") Integer sourceID, @FormParam("destination") Integer destinationID, @FormParam("amount") Float amount, @FormParam("author") String author) {

    }

    @POST
    @Path("apply")
    @Consumes("application/json")
    public void applyNewTransaction(@FormParam("source") Integer sourceID, @FormParam("destination") Integer destinationID, @FormParam("amount") Float amount, @FormParam("comment") String comment, @FormParam("author") String author) {

    }

    @POST
    @Path("validation")
    @Consumes("application/json")
    public void validationNewTransaction(@FormParam("transaction") Integer transactionID, @FormParam("approve") Boolean approve, @FormParam("author") String author) {

    }
}
