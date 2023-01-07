package com.ejbank.repository.utils;

import com.ejbank.entity.AccountEntity;
import com.ejbank.entity.AdvisorEntity;
import com.ejbank.entity.CustomerEntity;
import com.ejbank.entity.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Stateless
public class RepositoryUtils implements RepositoryUtilsLocal {

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    /**
     * Checks whether the given UserEntity parameter is an advisor or not.
     *
     * @param user The user whose status is to get checked. (UserEntity)
     * @return true if user is an advisor, else returns false. (boolean)
     */
    @Override
    public boolean isAdvisor(UserEntity user) {
        return user instanceof AdvisorEntity;
    }

    /**
     * Returns a list of customers as an Optional if the user parameter is an advisor. if the user parameter is a customer, returns a list
     * with a single customer in it as an Optional. If the uer is null, returns an empty list as an Optional.
     *
     * @param user The user from which the user type is going to be discovered. (UserEntity)
     * @param id The user id from which the list is going to be established. (int)
     * @return An Optional List filled with an advisor's customers (CustomerEntity), or a single CustomerEntity, or an
     * empty Optional. (Optional<List<CustomerEntity>>)
     */
    @Override
    public Optional<List<CustomerEntity>> getCustomersFromUser(UserEntity user, int id) {
        if (user instanceof AdvisorEntity) {
            var advisor = em.find(AdvisorEntity.class, id);
            return Optional.of(new ArrayList<>(advisor.getCustomers()));
        } else if (user instanceof CustomerEntity)
            return Optional.of(List.of(em.find(CustomerEntity.class, id)));
        else return Optional.empty();
    }

    /**
     * Checks if the given account id belongs to the given user id.
     * If
     *
     * @param accountID The account to compare with the userID parameter. (Integer)
     * @param userID The userID to compare with the accountID parameter. (Integer)
     * @param user The UserEntity to determine if the user is an advisor or a customer. (UserEntity)
     * @return An optional with the following possible values :
     *          - "The given ID does not match with any user"
     *          - "The account does not match with any of this advisor's customers" if user is an advisor
     *          - "The account does not match with any of yours" if user is a customer
     *          - An empty String Optional else.
     */
    @Override
    public Optional<String> isAccountReattachedToUser(Integer accountID, Integer userID, UserEntity user) {
        var customers = getCustomersFromUser(user, userID).orElse(null);
        AccountEntity account;
        CustomerEntity customer;

        if(customers == null)
            return Optional.of("The given ID does not correspond to any user");
        if (isAdvisor(user)) {
            account = customers.stream()
                    .map(CustomerEntity::getAccounts)
                    .flatMap(Collection::stream)
                    .filter(acc -> Objects.equals(acc.getId(), accountID))
                    .findFirst()
                    .orElse(null);
            if (account == null)
                return Optional.of("The account does not corresponds to a Customer for this Advisor");
        } else {
            customer = customers.get(0);
            account = customer.getAccounts().stream()
                    .filter(acc -> acc.getId() == accountID)
                    .findFirst()
                    .orElse(null);
            if (account == null)
                return Optional.of("The account does not corresponds to any of yours");
        }
        return Optional.empty();
    }
}