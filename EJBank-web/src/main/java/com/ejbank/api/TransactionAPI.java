package com.ejbank.api;

import com.ejbank.service.TransactionBean.TransactionBeanServiceImpl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TransactionAPI {
    @EJB
    private TransactionBeanServiceImpl transactionBeanService;

    @GET
    @Path("/test")
    public String testEJBFirstName() {
        return transactionBeanService.test();
    }
}
