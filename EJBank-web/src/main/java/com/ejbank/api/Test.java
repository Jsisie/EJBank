package com.ejbank.api;

import com.ejbank.payload.PeoplePayload;
import com.ejbank.service.TestBeanService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class Test {

    @EJB
    private TestBeanService testBean;
    
    @GET
    @Path("/ejb")
    public String testEJB() {
        return testBean.test();
    }

    @GET
    @Path("/getFirstName")
    public String testEJBFirstName() {
        return testBean.getFirstName(1);
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
