package com.arupkhanra.advanceSpringbootFeaturesAZ.controller;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Received request to fetch all users.");
        try {
            List<User> allUsers = userService.getAll();
            if (allUsers != null && !allUsers.isEmpty()) {
                log.info("Successfully fetched {} users.", allUsers.size());
                return new ResponseEntity<>(allUsers, HttpStatus.OK);
            } else {
                log.warn("No users found in the system.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching all users: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create-new-admin")
    public ResponseEntity<User> createUser(@RequestBody User newAdmin) {
        log.info("Received request to create a new admin user: {}", newAdmin.getUserName());
        try {
            User admin = userService.saveNewAdmin(newAdmin);
            log.info("Successfully created admin user with ID: {}", admin.getId());
            return new ResponseEntity<>(admin, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while creating admin user: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
