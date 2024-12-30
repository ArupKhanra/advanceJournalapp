package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
public interface UserService {
    User saveNewUser(User user);
    User updateUser(User user);
    void saveUser(User user);
    User findByUserName(String userName);

}



