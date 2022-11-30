package com.ejbank.api;

import com.ejbank.service.AccountBean.AccountBeanServiceImpl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountsAPI {
    @EJB
    private AccountBeanServiceImpl accountBeanService;

    @GET
    @Path("/test")
    public String testEJBFirstName() {
        return accountBeanService.test();
    }

    @GET
    @Path("/{user_id}")
    public String getAccountsFromUserId(@PathParam("user_id") Integer id) {
        return accountBeanService.test();
    }

    @GET
    @Path("/attached/{user_id}")
    public String getAttachedAccountsFromUserId(@PathParam("user_id") Integer id) {
        return accountBeanService.test();
    }

    @GET
    @Path("/all/{user_id}")
    public String getAllAccountsFromUserId(@PathParam("user_id") Integer id) {
        return accountBeanService.test();
    }

    @GET
    @Path("/{account_id}/{user_id}")
    public String getOneAccountFromUserId(@PathParam("account_id") Integer accountID, @PathParam("user_id") Integer userID) {
        return accountBeanService.test();
    }
}
