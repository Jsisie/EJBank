package com.ejbank.api;

import com.ejbank.payload.UserPayload;
import com.ejbank.service.TestBeanServiceImpl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserAPI {

    @EJB
    private TestBeanServiceImpl testBean;

    @GET
    @Path("/{id}")
    public UserPayload testEJBFirstName(@PathParam("id") Integer id) {
        return testBean.getName(id);
    }
}