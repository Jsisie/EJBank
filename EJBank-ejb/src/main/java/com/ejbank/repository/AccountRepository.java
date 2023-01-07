package com.ejbank.repository;

import com.ejbank.entity.AccountEntity;
import com.ejbank.entity.CustomerEntity;
import com.ejbank.entity.TransactionEntity;
import com.ejbank.entity.UserEntity;
import com.ejbank.payload.AccountPayload;
import com.ejbank.repository.utils.RepositoryUtilsLocal;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transaction;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import javax.persistence.Query;

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

        List<Double> monthlyInterestRates = new ArrayList<>();
        var year = Year.now().getValue();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date startOfYear = cal.getTime();
        while (cal.get(Calendar.YEAR) == year) {
            // Calculate the balance at the start of the month
            double balance = account.getBalance();
            Query queryFrom = em.createQuery("SELECT t FROM TransactionEntity t WHERE t.accountFrom = :account AND t.date < :startOfMonth");
            queryFrom.setParameter("account", account);
            queryFrom.setParameter("startOfMonth", cal.getTime());
            List<TransactionEntity> transactionsFrom = queryFrom.getResultList();
            for (TransactionEntity t : transactionsFrom) {
                balance += t.getAmount();
            }
            Query queryTo = em.createQuery("SELECT t FROM TransactionEntity t WHERE t.accountTo = :account AND t.date < :startOfMonth");
            queryTo.setParameter("account", account);
            queryTo.setParameter("startOfMonth", cal.getTime());
            List<TransactionEntity> transactionsTo = queryFrom.getResultList();
            for (TransactionEntity t : transactionsTo) {
                balance -= t.getAmount();
            }

            // Calculate the interest for the month
            double interestRate = account.getAccountType().getRate() / 12;
            double interest = balance * interestRate;
            monthlyInterestRates.add(interest);

            // Move to the next month
            cal.add(Calendar.MONTH, 1);
        }
        double sum = 0;
        for (double rate : monthlyInterestRates) {
            sum += rate;
        }
        double interest = sum/ monthlyInterestRates.size();
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
