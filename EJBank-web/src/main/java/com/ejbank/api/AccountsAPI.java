package com.ejbank.api;

import com.ejbank.payload.ListAccountsPayload;
import com.ejbank.service.AccountsBean.AccountsBeanServiceImpl;

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
    private AccountsBeanServiceImpl accountsBeanService;

    @GET
    @Path("/test")
    public String testEJBFirstName() {
        return accountsBeanService.test();
    }

    @GET
    @Path("/{user_id}")
    public ListAccountsPayload getAccountsFromUserId(@PathParam("user_id") Integer id) {
        return accountsBeanService.getAccounts(id);
    }

    @GET
    @Path("/attached/{user_id}")
    public ListAccountsPayload getAttachedAccountsFromUserId(@PathParam("user_id") Integer id) {
        return accountsBeanService.getAttachedAccounts(id);
    }

    @GET
    @Path("/all/{user_id}")
    public ListAccountsPayload getAllAccountsFromUserId(@PathParam("user_id") Integer id) {
        return accountsBeanService.getAllAccounts(id);
    }
}
