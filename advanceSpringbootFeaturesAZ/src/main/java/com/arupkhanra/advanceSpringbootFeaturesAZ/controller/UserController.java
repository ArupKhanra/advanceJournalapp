package com.arupkhanra.advanceSpringbootFeaturesAZ.controller;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.UserRepository;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.UserService;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/v1")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser) {
        String authenticatedUserName =SecurityContextHolder.getContext().getAuthentication().getName(); // Username from Basic Auth

        // Ensure the authenticated user matches the user to be updated
        if (!authenticatedUserName.equals(updatedUser.getUserName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authorized to update this user's information.");
        }
        // Proceed with the update
        User updated = userService.updateUser(updatedUser);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/deleteByUsername")
    public ResponseEntity<?> deleteByUsername(Authentication authentication) {
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
