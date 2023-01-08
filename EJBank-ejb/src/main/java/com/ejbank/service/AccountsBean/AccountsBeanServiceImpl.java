package com.ejbank.service.AccountsBean;

import com.ejbank.payload.AccountPayload;
import com.ejbank.payload.ListAccountsPayload;
import com.ejbank.repository.AccountsRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class AccountsBeanServiceImpl implements AccountsBeanService {

    @Inject
    private AccountsRepository accountsRepository;

    /**
     * AccountsBeanServiceImpl constructor.
     */
    public AccountsBeanServiceImpl() {
    }

    /**
     * Gathers all the accounts of a customer and returns them as a ListAccountsPayload.
     *
     * @param id The id of the user (customer) to retrieve the accounts from. (Integer)
     * @return A ListAccountsPayload of the customer's accounts, or "The User is not a customer" if the ID redirects
     * to an advisor (ListAccountPayload).
     */
    @Override
    public ListAccountsPayload getAccounts(Integer id) {
        return accountsRepository.getAccounts(id);
    }

    /**
     * Returns all the customer accounts an advisor has access to as a ListAccountsPayload. If the id is the one of a
     * customer, returns "The User is not an advisor" as a response.
     *
     * @param id The id of the user (advisor) to retrieve the customer accounts from. (Integer)
     * @return A ListAccountsPayload of containing an advisor's attached customer accounts. Or a ListAccountsPayload with
     * the message "The User is not an advisor" if the given id is one of a customer.
     */
    @Override
    public ListAccountsPayload getAttachedAccounts(Integer id) {
        return accountsRepository.getAttachedAccounts(id);
    }

    /**
     * If the given ID is one of an advisor : returns a ListAccountsPayload of its attached customer accounts.
     * Else, returns a ListAccountsPayload of all the accounts of the given customer.
     *
     * @param id The user we want to get the accounts or the attached accounts from. (Integer)
     * @return A ListAccountsPayload of all the advisor's attached customers accounts, or all the customer's account.
     * (ListAccountsPayload)
     */
    @Override
    public ListAccountsPayload getAllAccounts(Integer id) {
        return accountsRepository.getAllAccounts(id);
    }
}
