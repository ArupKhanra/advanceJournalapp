package com.arupkhanra.advanceSpringbootFeaturesAZ.controller;

import com.arupkhanra.advanceSpringbootFeaturesAZ.config.CustomUserDetailsServiceImpl;
import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.UserService;
import com.arupkhanra.advanceSpringbootFeaturesAZ.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
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


    @PostMapping("/create-new-admin")
    public ResponseEntity<User> createAdmin(@RequestBody User newAdmin) {
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

    @GetMapping
    ResponseEntity<List<User>> getUserForSA(){
        List<User> users = userService.getUserForSA();
       return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }

}
