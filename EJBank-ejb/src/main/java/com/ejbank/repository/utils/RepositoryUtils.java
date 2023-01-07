package com.ejbank.repository.utils;

import com.ejbank.entity.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.Year;
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
     * @param id   The user id from which the list is going to be established. (int)
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
     *
     * @param accountID The account to compare with the userID parameter. (Integer)
     * @param userID    The userID to compare with the accountID parameter. (Integer)
     * @param user      The UserEntity to determine if the user is an advisor or a customer. (UserEntity)
     * @return An optional with the following possible values :
     * - "The given ID does not match with any user"
     * - "The account does not match with any of this advisor's customers" if user is an advisor
     * - "The account does not match with any of yours" if user is a customer
     * - An empty String Optional else.
     */
    @Override
    public Optional<String> isAccountReattachedToUser(Integer accountID, Integer userID, UserEntity user) {
        var customers = getCustomersFromUser(user, userID).orElse(null);
        AccountEntity account;
        CustomerEntity customer;

        if (customers == null)
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


    @Override
    public Double getInterest(AccountEntity account) {
        List<Double> monthlyInterestRates = new ArrayList<>();
        var year = Year.now().getValue();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        while (cal.get(Calendar.YEAR) == year) {
            // Calculate the balance at the start of the month
            double balance = account.getBalance();
            Query queryFrom = em.createQuery("SELECT t FROM TransactionEntity t WHERE t.accountFrom = :account AND t.date < :startOfMonth");
            queryFrom.setParameter("account", account);
            queryFrom.setParameter("startOfMonth", cal.getTime());
            List<TransactionEntity> transactionsFrom = queryFrom.getResultList();
            for (TransactionEntity t : transactionsFrom)
                balance += t.getAmount();

            Query queryTo = em.createQuery("SELECT t FROM TransactionEntity t WHERE t.accountTo = :account AND t.date < :startOfMonth");
            queryTo.setParameter("account", account);
            queryTo.setParameter("startOfMonth", cal.getTime());
            List<TransactionEntity> transactionsTo = queryFrom.getResultList();
            for (TransactionEntity t : transactionsTo)
                balance -= t.getAmount();

            // Calculate the interest for the month
            double interestRate = account.getAccountType().getRate() / 12;
            double interest = balance * interestRate;
            monthlyInterestRates.add(interest);

            // Move to the next month
            cal.add(Calendar.MONTH, 1);
        }
        double sum = monthlyInterestRates.stream().mapToDouble(rate -> rate).sum();
        return sum / monthlyInterestRates.size();
    }
}