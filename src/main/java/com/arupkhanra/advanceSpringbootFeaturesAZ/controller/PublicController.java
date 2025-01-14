package com.arupkhanra.advanceSpringbootFeaturesAZ.controller;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private UserService userService;

    // localhost:8081/public/createUser
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Received request to create a new user: {}", user);

        try {
            User userSave = userService.saveNewUser(user);
            logger.info("User successfully created with details: {}", userSave);
            return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
        } catch (Exception e) {
            logger.error("Error occurred while creating user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/healthCheck")
    public ResponseEntity<Map<String, Object>> healthCheckApplication() {
        logger.info("Health check endpoint accessed.");
        Map<String, Object> healthStatus = new HashMap<>();
        healthStatus.put("status", "HEALTH_IS_OK");
        healthStatus.put("timestamp", System.currentTimeMillis());
        logger.info("Health check response: {}", healthStatus);
        return ResponseEntity.ok(healthStatus);
    }

    @GetMapping
    ResponseEntity<List<User>> getUserForSA(){
        List<User> users = userService.getUserForSA();
       return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
