package com.ejbank.api;

import com.ejbank.service.AccountBean.AccountBeanServiceImpl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
}
