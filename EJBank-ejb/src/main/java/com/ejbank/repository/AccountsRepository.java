package com.ejbank.repository;

import com.ejbank.entity.CustomerEntity;
import com.ejbank.entity.UserEntity;
import com.ejbank.payload.AccountsPayload;
import com.ejbank.payload.ListAccountsPayload;
import com.ejbank.repository.utils.RepositoryUtilsLocal;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
public class AccountsRepository {
    @EJB
    private RepositoryUtilsLocal utils;

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    /**
     * Gathers all the accounts of a customer and returns them as a ListAccountsPayload.
     *
     * @param id The id of the user (customer) to retrieve the accounts from. (Integer)
     * @return A ListAccountsPayload of the customer's accounts, or "The User is not a customer" if the ID redirects
     * to an advisor (ListAccountPayload).
     */
    public ListAccountsPayload getAccounts(Integer id) {
        var user = em.find(UserEntity.class, id);
        if (utils.isAdvisor(user))
            return new ListAccountsPayload("The User is not a Customer");

        var accountList = new ArrayList<AccountsPayload>();
        List<CustomerEntity> customers = utils.getCustomersFromUser(user, id).orElse(null);
        if(customers == null)
            return new ListAccountsPayload("The given ID does not correspond to any user");

        customers.forEach(customer -> customer.getAccounts().forEach(account -> accountList.add(new AccountsPayload(
                account.getId(),
                account.getAccountType().getName(),
                account.getBalance()
        ))));
        return new ListAccountsPayload(accountList);
    }

    /**
     * Returns all the customer accounts an advisor has access to as a ListAccountsPayload. If the id is the one of a
     * customer, returns "The User is not an advisor" as a response.
     *
     * @param id The id of the user (advisor) to retrieve the customer accounts from. (Integer)
     * @return A ListAccountsPayload of containing an advisor's attached customer accounts. Or a ListAccountsPayload with
     * the message "The User is not an advisor" if the given id is one of a customer.
     */
    public ListAccountsPayload getAttachedAccounts(Integer id) {
        var user = em.find(UserEntity.class, id);
        if (!utils.isAdvisor(user))
            return new ListAccountsPayload("The User is not an advisor");
        var accountList = new ArrayList<AccountsPayload>();
        List<CustomerEntity> customers = utils.getCustomersFromUser(user, id).orElse(null);
        if(customers == null)
            return new ListAccountsPayload("The given ID does not correspond to any user");

        customers.forEach(customer -> customer.getAccounts().forEach(account -> accountList.add(new AccountsPayload(
                account.getId(),
                account.getCustomer().getFirstname(),
                account.getCustomer().getLastname(),
                account.getAccountType().getName(),
                account.getBalance(),
                account.getTransactionsFrom().stream().filter(transactionEntity -> !transactionEntity.getApplied()).toList().size()
        ))));
        return new ListAccountsPayload(accountList);
    }

    /**
     * If the given ID is one of an advisor : returns a ListAccountsPayload of its attached customer accounts.
     * Else, returns a ListAccountsPayload of all the accounts of the given customer.
     *
     * @param id The user we want to get the accounts or the attached accounts from. (Integer)
     * @return A ListAccountsPayload of all the advisor's attached customers accounts, or all the customer's account.
     * (ListAccountsPayload)
     */
    public ListAccountsPayload getAllAccounts(Integer id) {
        var user = em.find(UserEntity.class, id);
        return utils.isAdvisor(user)?getAttachedAccounts(id):getAccounts(id);
    }
}
