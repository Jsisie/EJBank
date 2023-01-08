package com.ejbank.service.AccountBean;

import com.ejbank.payload.AccountPayload;
import com.ejbank.payload.ListAccountsPayload;
import com.ejbank.repository.AccountRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class AccountBeanServiceImpl implements AccountBeanService {

    @Inject
    private AccountRepository accountRepository;

    /**
     * AccountBeanServiceImpl constructor.
     */
    public AccountBeanServiceImpl() {
    }

    /**
     * Returns a user's account as an AccountPayload.
     *
     * @param accountID The id of the account that is going to be searched for. (Integer)
     * @param userID The id of the supposed account's owner. (Integer)
     * @return The specified account for the specified user as an AccountPayload. Returns an error AccountPayload if :
     *      * - The given ID does not match with any user's.
     *      * - The user is an advisor and the account  isn't part of his supervised accounts.
     *      * - The user is a customer and the account doesnt belong to it.
     *      (AccountPayload)
     */
    @Override
    public AccountPayload getAccount(Integer accountID, Integer userID) {
        return accountRepository.getAccount(accountID, userID);
    }
}
