package com.example.managementblog.Postgres.controller;

import com.example.managementblog.Postgres.model.User;
import com.example.managementblog.Postgres.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            boolean created = userService.registerUser(user);
            if (created) {
                return ResponseEntity.ok("User registered successfully");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email or username already exists");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the real error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }
}
