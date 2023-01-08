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

    /**
     * Gets a first name and a last name from a user's id.
     *
     * @param id the id of the user. (int)
     * @return The first name and the last name of a user as a UserPayload. (UserPayload)
     */
    @Override
    public UserPayload getName(int id) {
        return userRepository.getName(id);
    }
}
