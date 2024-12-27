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
    public User createUser(User user) {
        log.info("Attempting to save user with ID: {}", user.getUserId());

        try {
            user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));  // Set roles before saving

            User userEntry = userRepository.save(user);
            log.info("Successfully saved user with ID: {}", userEntry.getUserId());

            return userEntry;
        } catch (Exception e) {
            log.error("Error while saving user: ", e);
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
            log.info("Successfully updated user with ID: {}", savedUser.getUserId());
            userRepository.save(savedUser);
            return savedUser;
        } else {
            log.error("User with username {} not found for update.", updatedUser.getUserName());
            throw new UserNotFoundException("User with username " + updatedUser.getUserName() + " not found.");
        }
    }

}
