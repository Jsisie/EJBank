package com.ejbank.repository;

import com.ejbank.entity.AdvisorEntity;
import com.ejbank.entity.CustomerEntity;
import com.ejbank.entity.UserEntity;
import com.ejbank.payload.AccountPayload;
import com.ejbank.payload.AccountsPayload;
import com.ejbank.payload.ListAccountsPayload;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
public class AccountRepository {

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    private boolean isAdvisor(UserEntity user) {
        return user instanceof AdvisorEntity;
    }

    private Optional<List<CustomerEntity>> getCustomerOrAdvisor(UserEntity user, int id) {
        if (isAdvisor(user)) {
            var advisor = em.find(AdvisorEntity.class, id);
            return Optional.of(new ArrayList<>(advisor.getCustomers()));
        } else if (user instanceof CustomerEntity)
            return Optional.of(List.of(em.find(CustomerEntity.class, id)));
        else return Optional.empty();
    }

    public AccountPayload getAccount(Integer accountID, Integer userID) {
        // TODO - Incorrect + Disgusting
        var user = em.find(UserEntity.class, userID);
        if (isAdvisor(user))
            return new AccountPayload("The User is not a Customer");

        List<CustomerEntity> customers = getCustomerOrAdvisor(user, userID).orElseThrow(IllegalArgumentException::new);
        var customer = customers.get(0);
        var accounts = customer.getAccounts();

        var account = accounts.stream()
                .filter(acc -> acc.getId() == accountID)
                .findFirst()
                .orElse(null);

        var advisor = em.find(AdvisorEntity.class, customer.getAdvisorId());
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
