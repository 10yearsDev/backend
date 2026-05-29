package com.example.backend.service;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RegisterRequest;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository userRepository;

    public String register(RegisterRequest request) {

        boolean exists = userRepository
                .findByUsername(request.getUsername())
                .isPresent();

        if (exists) {
            return "Username already exists";
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        userRepository.save(user);

        return "Register success";
    }

    public String login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElse(null);

        if (user == null) {
            return "User not found";
        }

        if (!user.getPassword()
                .equals(request.getPassword())) {

            return "Wrong password";
        }

        return "Login success";
    }
}