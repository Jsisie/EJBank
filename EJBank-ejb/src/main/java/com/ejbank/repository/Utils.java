package com.ejbank.repository;

import com.ejbank.entity.AdvisorEntity;
import com.ejbank.entity.UserEntity;

class Utils {
    static boolean isAdvisor(UserEntity user) {
        return user instanceof AdvisorEntity;
    }
}
