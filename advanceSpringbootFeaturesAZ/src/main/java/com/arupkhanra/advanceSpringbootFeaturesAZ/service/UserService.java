package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;

import java.util.List;

public interface UserService {
    User saveNewUser(User user);
    User updateUser(User user);
    void saveUser(User user);
    User findByUserName(String userName);

    List<User> getAll();

    User saveNewAdmin(User user);
}



