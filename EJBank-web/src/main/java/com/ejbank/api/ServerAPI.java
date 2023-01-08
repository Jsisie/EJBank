package com.ejbank.api;

import com.ejbank.payload.ServerPayload;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/server")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ServerAPI {

    /**
     * Checks the status of the server. If the method returns, then the server is up.
     *
     * @return A new ServerPayload if the server is up. Else, does not return.
     */
    @GET
    @Path("/status")
    public ServerPayload getStatus() {
        return new ServerPayload(true);
    }
}
