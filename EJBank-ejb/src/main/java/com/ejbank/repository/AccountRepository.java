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

@Stateless
@LocalBean
public class AccountRepository {

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    private List<CustomerEntity> getCustomerOrAdvisor(UserEntity user, int id) {
        if (user instanceof AdvisorEntity) {
            var advisor = em.find(AdvisorEntity.class, id);
            return new ArrayList<>(advisor.getCustomers());
        } else if (user instanceof CustomerEntity)
            return List.of(em.find(CustomerEntity.class, id));
        else
            throw new IllegalStateException("The id given in parameters doesn't corresponds to any account");
    }

    public ListAccountsPayload getAccounts(Integer id) {
        var user = em.find(UserEntity.class, id);
        var accountList = new ArrayList<AccountsPayload>();
        List<CustomerEntity> customers = getCustomerOrAdvisor(user, id);

        customers.forEach(customer -> customer.getAccounts().forEach(account -> accountList.add(new AccountsPayload(
                account.getId(),
                account.getAccountType().getName(),
                account.getBalance()
        ))));
        return new ListAccountsPayload(accountList);
    }

    public ListAccountsPayload getAttachedAccounts(Integer id) {
        var user = em.find(UserEntity.class, id);
        var accountList = new ArrayList<AccountsPayload>();
        List<CustomerEntity> customers = getCustomerOrAdvisor(user, id);

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
        // TODO - Nothing done for now, juste copy/paste from above method
        var user = em.find(UserEntity.class, id);
        var accountList = new ArrayList<AccountsPayload>();
        List<CustomerEntity> customers = getCustomerOrAdvisor(user, id);

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
}
