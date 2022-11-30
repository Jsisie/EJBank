package com.ejbank.service.UserBean;

import com.ejbank.payload.UserPayload;
import com.ejbank.repository.UserRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@LocalBean
public class UserBeanServiceImpl implements UserBeanService {

    @Inject
    private UserRepository userRepository;

    public UserBeanServiceImpl() {
    }

    @Override
    public UserPayload getName(int id) {
        return userRepository.getName(id);
    }
}
