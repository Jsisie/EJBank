package com.ejbank.repository;

import com.ejbank.entity.AccountEntity;
import com.ejbank.entity.CustomerEntity;
import com.ejbank.entity.UserEntity;
import com.ejbank.payload.AccountPayload;
import com.ejbank.repository.utils.RepositoryUtilsLocal;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Stateless
@LocalBean
public class AccountRepository {
    @EJB
    private RepositoryUtilsLocal utils;

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    /**
     *
     * @param accountID
     * @param userID
     * @return
     */
    public AccountPayload getAccount(Integer accountID, Integer userID) {
        AccountEntity account;
        CustomerEntity customer;
        var user = em.find(UserEntity.class, userID);
        var customers = utils.getCustomersFromUser(user, userID).orElse(null);
        if(customers == null)
            return new AccountPayload("The given ID does not correspond to any user");

        if (utils.isAdvisor(user)) {
            account = customers.stream()
                    .map(CustomerEntity::getAccounts)
                    .flatMap(Collection::stream)
                    .filter(acc -> Objects.equals(acc.getId(), accountID))
                    .findFirst()
                    .orElse(null);
            if (account == null)
                return new AccountPayload("The account does not corresponds to a Customer for this Advisor");
            customer = account.getCustomer();
        } else {
            customer = customers.get(0);
            account = customer.getAccounts().stream()
                    .filter(acc -> acc.getId() == accountID)
                    .findFirst()
                    .orElse(null);
            if (account == null)
                return new AccountPayload("The account does not corresponds to any of yours");
        }
        var advisor = customer.getAdvisor();
        int interest = 0; // TODO - process the interest
        return new AccountPayload(
                customer.getFirstname(),
                customer.getLastname(),
                advisor.getFirstname(),
                advisor.getLastname(),
                account.getAccountType().getRate(),
                interest,
                account.getBalance()
        );
    }
}
