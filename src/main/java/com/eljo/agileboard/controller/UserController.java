package com.eljo.agileboard.controller;

import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidUserException;
import com.eljo.agileboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Eljo.George on 10/14/2018.
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() throws InvalidUserException {
        return ResponseEntity.ok().body(userService.getCurrentUser());
    }
}
