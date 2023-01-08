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
import java.util.Collection;
import java.util.Objects;

@Stateless
@LocalBean
public class AccountRepository {
    @EJB
    private RepositoryUtilsLocal utils;

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    /**
     * Get an account from a user as an AccountPayLoad. If the user is a customer, can only return one of his own
     * accounts. If the user is an advisor, can only return one of his attached accounts.
     *
     * @param accountID The id of the account that is going to be searched for. (Integer)
     * @param userID The id of the user whose account is wanted. (Integer)
     * @return The specified account for the specified user as an AccountPayload. Returns an error AccountPayload if :
     * - The given ID does not match with any user's.
     * - The user is an advisor and the account  isn't part of his supervised accounts.
     * - The user is a customer and the account doesn't belong to it.
     * (AccountPayload)
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

        var interest = utils.getInterest(account);

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
