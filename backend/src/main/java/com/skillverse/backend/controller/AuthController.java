package com.skillverse.backend.controller;

import com.skillverse.backend.dto.AuthResponse;
import com.skillverse.backend.dto.LoginRequest;
import com.skillverse.backend.dto.RegisterRequest;
import com.skillverse.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
public AuthResponse login(@RequestBody LoginRequest request) {
    return userService.login(request);
}
}