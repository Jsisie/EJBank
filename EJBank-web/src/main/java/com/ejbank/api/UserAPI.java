package com.ejbank.api;

import com.ejbank.payload.UserPayload;
import com.ejbank.service.UserBean.UserBeanServiceImpl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserAPI {

    @EJB
    private UserBeanServiceImpl userBean;

    /**
     * Test method.
     * Scope : "/user"
     * Gets a first name and a last name from a user's id.
     *
     * @param id the id of the user. (int)
     * @return The first name and the last name of a user as a UserPayload. (UserPayload)
     */
    @GET
    @Path("/{id}")
    public UserPayload testEJBFirstName(@PathParam("id") Integer id) {
        return userBean.getName(id);
    }
}