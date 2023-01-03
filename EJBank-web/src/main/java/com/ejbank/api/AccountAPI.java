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

    @GET
    @Path("/test")
    public String testEJBFirstName() {
        return accountBeanService.test();
    }

    @GET
    @Path("/{account_id}/{user_id}")
    public AccountPayload getAccount(@PathParam("account_id") Integer accountID, @PathParam("user_id") Integer userID) {
        return accountBeanService.getAccount(accountID, userID);
    }
}
