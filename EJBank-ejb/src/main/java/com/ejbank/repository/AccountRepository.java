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

@Stateless
@LocalBean
public class AccountRepository {

    @PersistenceContext(unitName = "EJBankPU")
    private EntityManager em;

    public ListAccountsPayload getAccountsFromUserId(Integer id) {
        var user = em.find(UserEntity.class, id);
        var accountList = new ArrayList<AccountsPayload>();
        var customers = new ArrayList<CustomerEntity>();

        if (user instanceof AdvisorEntity) {
            var advisor = em.find(AdvisorEntity.class, id);
            customers = new ArrayList<>(advisor.getCustomers());
        } else if (user instanceof CustomerEntity)
            customers.add(em.find(CustomerEntity.class, id));
        // TODO - IF id NOT PRESENT, EMPTY LIST IS RETURNED (BAD !!)
//        else
//            throw new Exception();

        customers.forEach(customer -> customer.getAccounts().forEach(account -> accountList.add(new AccountsPayload(
                account.getId(),
                account.getAccountType().getName(),
                account.getBalance()
        ))));
        return new ListAccountsPayload(accountList);
    }
}
