package com.taskmanagementservice.controller;

import com.taskmanagementservice.business.logic.service.UserService;
import com.taskmanagementservice.utils.requests.CreateUserRequest;
import com.taskmanagementservice.utils.requests.LoginRequest;
import com.taskmanagementservice.utils.responses.JwtResponse;
import com.taskmanagementservice.utils.responses.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, Locale locale) {
        JwtResponse jwtResponse = userService.login(loginRequest, locale);
        return ResponseEntity.status(jwtResponse.getStatusCode()).body(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserRequest createUserRequest, Locale locale) {
        UserResponse response = userService.create(createUserRequest, locale);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
