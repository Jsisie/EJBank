package com.ejbank.repository;

import com.ejbank.entity.AccountEntity;
import com.ejbank.entity.AdvisorEntity;
import com.ejbank.entity.CustomerEntity;
import com.ejbank.entity.UserEntity;
import com.ejbank.payload.AccountPayload;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Stateless
@LocalBean
public class AccountRepository {

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    private Optional<List<CustomerEntity>> getCustomerOrAdvisor(UserEntity user, int id) {
        if (user instanceof AdvisorEntity) {
            var advisor = em.find(AdvisorEntity.class, id);
            return Optional.of(new ArrayList<>(advisor.getCustomers()));
        } else if (user instanceof CustomerEntity)
            return Optional.of(List.of(em.find(CustomerEntity.class, id)));
        else return Optional.empty();
    }

    public AccountPayload getAccount(Integer accountID, Integer userID) {
        AccountEntity account;
        CustomerEntity customer;
        var user = em.find(UserEntity.class, userID);
        var customers = getCustomerOrAdvisor(user, userID).orElse(null);
        if(customers == null)
            return new AccountPayload("The given ID does not correspond to any user");
        if (Utils.isAdvisor(user)) {
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
        }

        if (account == null)
            return new AccountPayload("Account ID provided doesn't exist");

        var advisor = customer.getAdvisor();

        return new AccountPayload(
                customer.getFirstname(),
                customer.getLastname(),
                advisor.getFirstname(),
                advisor.getLastname(),
                account.getAccountType().getRate(),
                account.getAccountType().getOverdraft(),
                account.getBalance()
        );
    }
}
