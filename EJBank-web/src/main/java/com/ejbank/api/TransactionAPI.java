package com.ejbank.api;

import com.ejbank.payload.UserPayload;
import com.ejbank.service.TestBeanServiceImpl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/aacounts")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class TransactionAPI {
    @EJB
    private TestBeanServiceImpl testBean;

    @GET
    @Path("/{id}")
    public UserPayload testEJBFirstName(@PathParam("id") Integer id) {
        return testBean.getName(id);
    }
}