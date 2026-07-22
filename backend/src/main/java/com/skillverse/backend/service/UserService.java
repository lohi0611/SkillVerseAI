package com.skillverse.backend.service;

import com.skillverse.backend.dto.AuthResponse;
import com.skillverse.backend.security.JwtService;
import com.skillverse.backend.dto.LoginRequest;
import com.skillverse.backend.dto.RegisterRequest;
import com.skillverse.backend.entity.User;
import com.skillverse.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                   PasswordEncoder passwordEncoder,
                   JwtService jwtService) {

    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
}

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }
    public AuthResponse login(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElse(null);

    if (user == null) {
        return new AuthResponse(
                "User not found",
                null
        );
    }

    if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPassword())) {

        return new AuthResponse(
                "Invalid password",
                null
        );
    }

    String token = jwtService.generateToken(user.getEmail());

    return new AuthResponse(
            "Login Successful",
            token
    );
}
}