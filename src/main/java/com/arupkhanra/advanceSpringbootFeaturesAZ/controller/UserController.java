package com.arupkhanra.advanceSpringbootFeaturesAZ.controller;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.externalAPI.WeatherResponse;
import com.arupkhanra.advanceSpringbootFeaturesAZ.externalAPI.WeatherService;
import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.UserRepository;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/v1")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser) {
        String authenticatedUserName = SecurityContextHolder.getContext().getAuthentication().getName(); // Username from Basic Auth

        log.info("Request received to update user: {}", updatedUser);
        log.info("Authenticated username: {}", authenticatedUserName);

        if (!authenticatedUserName.equals(updatedUser.getUserName())) {
            log.warn("Unauthorized update attempt by user: {}", authenticatedUserName);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authorized to update this user's information.");
        }

        try {
            User updated = userService.updateUser(updatedUser);
            log.info("User updated successfully: {}", updated);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            log.error("Error occurred while updating user: {}", updatedUser.getUserName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update user. Please try again later.");
        }
    }

    @Transactional
    @DeleteMapping("/deleteByUsername")
    public ResponseEntity<?> deleteByUsername(Authentication authentication) {
        String authenticatedUserName = authentication.getName();
        log.info("Request received to delete user by username: {}", authenticatedUserName);

        try {
            userRepository.deleteByUserName(authenticatedUserName);
            log.info("User deleted successfully: {}", authenticatedUserName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error occurred while deleting user: {}", authenticatedUserName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete user. Please try again later.");
        }
    }
    @GetMapping("/weatherAPI")
    public ResponseEntity<?> greeting() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String greeting = "Hi " + name;
        try {
            WeatherResponse weatherResponse = weatherService.getWeather("kolkata");

            if (weatherResponse != null && weatherResponse.getCurrent() != null) {
                greeting += ", weather feels like " + weatherResponse.getCurrent().getFeelsLike() + "°C";
            } else {
                log.warn("No weather data available for Kolkata.");
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching weather data", e);
            greeting += ", but we couldn't fetch the weather information at the moment.";
        }

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}
