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

        try {
            user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            User userEntry = userRepository.save(user);
            return userEntry;
        } catch (Exception e) {
            throw new RuntimeException("Error while saving user: " + e.getMessage(), e);
        }
    }
    @Override
    public User updateUser(User updatedUser) {
        User existingUser = userRepository.findByUserName(updatedUser.getUserName());
        if (existingUser != null) {
            existingUser.setPassword(PASSWORD_ENCODER.encode(updatedUser.getPassword())); // Update password
            // Update other fields as needed
            User savedUser = userRepository.save(existingUser);
            log.info("Successfully updated user with ID: {}", savedUser.getId());
            userRepository.save(savedUser);
            return savedUser;
        } else {
            log.error("User with username {} not found for update.", updatedUser.getUserName());
            throw new UserNotFoundException("User with username " + updatedUser.getUserName() + " not found.");
        }
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
