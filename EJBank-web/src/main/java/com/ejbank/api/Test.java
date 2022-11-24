package com.ejbank.api;

import com.ejbank.payload.PeoplePayload;
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
    @Path("/getFirstName/{id}")
    public String testEJBFirstName(@PathParam("id") Integer id) {
        return testBean.getFirstName(id);
    }

    @GET
    @Path("/people/{age}")
    public PeoplePayload testPayloadResponse(@PathParam("age") Integer age) {
        return new PeoplePayload("Jean", "Dupont", age);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/post")
    public String testPostRequest(PeoplePayload payload) {
        return String.format("%s - %s", payload.firstname(), payload.lastname());
    }
}
