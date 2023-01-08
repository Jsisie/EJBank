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

    /**
     * Test method
     * Returns a test String.
     *
     * @return A test String "test Account". (String)
     */
    @GET
    @Path("/test")
    public String testEJBFirstName() {
        return accountsBeanService.test();
    }

    /**
     * Gathers all the accounts of a customer and returns them as a ListAccountsPayload.
     *
     * @param id The id of the user (customer) to retrieve the accounts from. (Integer)
     * @return A ListAccountsPayload of the customer's accounts, or "The User is not a customer" if the ID redirects
     * to an advisor (ListAccountPayload).
     */
    @GET
    @Path("/{user_id}")
    public ListAccountsPayload getAccountsFromUserId(@PathParam("user_id") Integer id) {
        return accountsBeanService.getAccounts(id);
    }

    /**
     * Returns all the customer accounts an advisor has access to as a ListAccountsPayload. If the id is the one of a
     * customer, returns "The User is not an advisor" as a response.
     *
     * @param id The id of the user (advisor) to retrieve the customer accounts from. (Integer)
     * @return A ListAccountsPayload of containing an advisor's attached customer accounts. Or a ListAccountsPayload with
     * the message "The User is not an advisor" if the given id is one of a customer.
     */
    @GET
    @Path("/attached/{user_id}")
    public ListAccountsPayload getAttachedAccountsFromUserId(@PathParam("user_id") Integer id) {
        return accountsBeanService.getAttachedAccounts(id);
    }

    /**
     * If the given ID is one of an advisor : returns a ListAccountsPayload of its attached customer accounts.
     * Else, returns a ListAccountsPayload of all the accounts of the given customer.
     *
     * @param id The user we want to get the accounts or the attached accounts from. (Integer)
     * @return A ListAccountsPayload of all the advisor's attached customers accounts, or all the customer's account.
     * (ListAccountsPayload)
     */
    @GET
    @Path("/all/{user_id}")
    public ListAccountsPayload getAllAccountsFromUserId(@PathParam("user_id") Integer id) {
        return accountsBeanService.getAllAccounts(id);
    }
}
