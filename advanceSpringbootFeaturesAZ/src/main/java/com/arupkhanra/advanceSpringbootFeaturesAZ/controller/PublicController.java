package com.arupkhanra.advanceSpringbootFeaturesAZ.controller;

import com.arupkhanra.advanceSpringbootFeaturesAZ.entity.User;
import com.arupkhanra.advanceSpringbootFeaturesAZ.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    // localhost:8081/public/createUser
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){

        User userSave = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }
}
