package com.ejbank.api;

import com.ejbank.payload.UserPayload;
import com.ejbank.service.TestBeanServiceImpl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Test {

    @EJB
    private TestBeanServiceImpl testBean;

    @GET
    @Path("/ejb")
    public String testEJB() {
        return testBean.test();
    }

    @GET
    @Path("/people/{age}")
    public UserPayload testPayloadResponse(@PathParam("age") Integer age) {
        return new UserPayload("Jean", "Dupont", age);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/post")
    public String testPostRequest(UserPayload payload) {
        return String.format("%s - %s", payload.getFirstname(), payload.getLastname());
    }
}
