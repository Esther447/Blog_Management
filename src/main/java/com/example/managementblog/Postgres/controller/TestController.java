package com.example.managementblog.Postgres.controller;

import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content. Here you can see all endpoints.";
    }
    @GetMapping("/user")
    public String userAccess() {
        return "User Content. Here you can see only user endpoints.";
    }
}