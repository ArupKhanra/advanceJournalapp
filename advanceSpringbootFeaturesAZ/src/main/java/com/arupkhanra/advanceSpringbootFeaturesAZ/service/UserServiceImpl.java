package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.exceptions.UserNotFoundException;
import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public User saveNewUser(User user) {
        log.trace("Entering saveNewUser() with user: {}", user);
        try {
            user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            User userEntry = userRepository.save(user);
            log.info("Successfully saved new user with ID: {}", userEntry.getId());
            return userEntry;
        } catch (Exception e) {
            log.error("Error while saving new user: {}", e.getMessage(), e);
            throw new RuntimeException("Error while saving user: " + e.getMessage(), e);
        }
    }

    @Override
    public User updateUser(User updatedUser) {
        log.trace("Entering updateUser() with updatedUser: {}", updatedUser);
        User existingUser = userRepository.findByUserName(updatedUser.getUserName());
        if (existingUser != null) {
            existingUser.setPassword(PASSWORD_ENCODER.encode(updatedUser.getPassword())); // Update password
            // Update other fields as needed
            User savedUser = userRepository.save(existingUser);
            log.info("Successfully updated user with ID: {}", savedUser.getId());
            return savedUser;
        } else {
            log.error("User with username {} not found for update.", updatedUser.getUserName());
            throw new UserNotFoundException("User with username " + updatedUser.getUserName() + " not found.");
        }
    }

    @Override
    public void saveUser(User user) {
        log.trace("Entering saveUser() with user: {}", user);
        userRepository.save(user);
        log.info("Successfully saved user with username: {}", user.getUserName());
    }

    @Override
    public User findByUserName(String userName) {
        log.debug("Attempting to find user with username: {}", userName);
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            log.info("User with username {} found: {}", userName, user);
        } else {
            log.warn("User with username {} not found.", userName);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        log.trace("Entering getAll()");
        List<User> users = userRepository.findAll();
        log.info("Retrieved all users. Total count: {}", users.size());
        return users;
    }

    @Override
    public User saveNewAdmin(User user) {
        log.trace("Entering saveNewAdmin() with user: {}", user);
        try {
            user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
            user.setRoles(Arrays.asList("User", "ADMIN"));
            User userEntry = userRepository.save(user);
            log.info("Successfully saved new admin with ID: {}", userEntry.getId());
            return userEntry;
        } catch (Exception e) {
            log.error("Error while saving new admin: {}", e.getMessage(), e);
            throw new RuntimeException("Error while saving admin: " + e.getMessage(), e);
        }
    }
}
