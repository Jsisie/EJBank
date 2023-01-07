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
     *
     * @param user
     * @return
     */
    @Override
    public boolean isAdvisor(UserEntity user) {
        return user instanceof AdvisorEntity;
    }

    /**
     *
     * @param user
     * @param id
     * @return
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
     *
     * @param accountID
     * @param userID
     * @param user
     * @return
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