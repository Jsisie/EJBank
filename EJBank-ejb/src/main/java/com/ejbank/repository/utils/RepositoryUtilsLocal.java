package com.ejbank.repository.utils;

import com.ejbank.entity.CustomerEntity;
import com.ejbank.entity.UserEntity;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface RepositoryUtilsLocal {

    boolean isAdvisor(UserEntity user);
    Optional<List<CustomerEntity>> getCustomerOrAdvisor(UserEntity user, int id);
    Optional<String> isAccountReattachedToUser(Integer accountID, Integer userID, UserEntity user);
}
