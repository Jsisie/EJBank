package com.ejbank.api;

import com.ejbank.payload.AccountPayload;
import com.ejbank.service.AccountBean.AccountBeanServiceImpl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountAPI {
    @EJB
    private AccountBeanServiceImpl accountBeanService;

    /**
     * Test method
     * Returns a test String.
     *
     * @return A test String "test Account". (String)
     */
    @GET
    @Path("/test")
    public String testEJBFirstName() {
        return accountBeanService.test();
    }

    /**
     * Returns a user's account as an AccountPayload.
     *
     * @param accountID The id of the account that is going to be searched for. (Integer)
     * @param userID The id of the supposed account's owner. (Integer)
     * @return The specified account for the specified user as an AccountPayload. Returns an error AccountPayload if :
     *      - The given ID does not match with any user's.
     *      - The user is an advisor and the account  isn't part of his supervised accounts.
     *      - The user is a customer and the account doesnt belong to it.
     *      (AccountPayload)
     */
    @GET
    @Path("/{account_id}/{user_id}")
    public AccountPayload getAccount(@PathParam("account_id") Integer accountID, @PathParam("user_id") Integer userID) {
        return accountBeanService.getAccount(accountID, userID);
    }
}
