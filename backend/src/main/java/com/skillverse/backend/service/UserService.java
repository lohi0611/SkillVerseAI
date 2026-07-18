package com.skillverse.backend.service;

import com.skillverse.backend.dto.RegisterRequest;
import com.skillverse.backend.entity.User;
import com.skillverse.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor Injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        // ensure user is non-null to satisfy null-safety contracts
        Objects.requireNonNull(user, "user must not be null");
        userRepository.save(user);

        return "User Registered Successfully";
    }
}