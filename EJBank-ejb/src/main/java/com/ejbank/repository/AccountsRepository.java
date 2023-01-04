package com.ejbank.repository;

import com.ejbank.entity.AdvisorEntity;
import com.ejbank.entity.CustomerEntity;
import com.ejbank.entity.UserEntity;
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
public class AccountsRepository {

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

    public ListAccountsPayload getAccounts(Integer id) {
        var user = em.find(UserEntity.class, id);
        if (isAdvisor(user))
            return new ListAccountsPayload("The User is not a Customer");

        var accountList = new ArrayList<AccountsPayload>();
        List<CustomerEntity> customers = getCustomerOrAdvisor(user, id).orElseThrow(IllegalArgumentException::new);

        customers.forEach(customer -> customer.getAccounts().forEach(account -> accountList.add(new AccountsPayload(
                account.getId(),
                account.getAccountType().getName(),
                account.getBalance()
        ))));
        return new ListAccountsPayload(accountList);
    }

    public ListAccountsPayload getAttachedAccounts(Integer id) {
        var user = em.find(UserEntity.class, id);
        if (!isAdvisor(user))
            return new ListAccountsPayload("The User is not an advisor");
        var accountList = new ArrayList<AccountsPayload>();
        List<CustomerEntity> customers = getCustomerOrAdvisor(user, id).orElseThrow(IllegalArgumentException::new);

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

    public ListAccountsPayload getAllAccounts(Integer id) {
        var user = em.find(UserEntity.class, id);
        return isAdvisor(user)?getAttachedAccounts(id):getAccounts(id);
    }
}
