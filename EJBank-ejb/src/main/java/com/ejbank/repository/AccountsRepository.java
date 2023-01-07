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
     *
     * @param id
     * @return
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
     *
     * @param id
     * @return
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
     *
     * @param id
     * @return
     */
    public ListAccountsPayload getAllAccounts(Integer id) {
        var user = em.find(UserEntity.class, id);
        return utils.isAdvisor(user)?getAttachedAccounts(id):getAccounts(id);
    }
}
