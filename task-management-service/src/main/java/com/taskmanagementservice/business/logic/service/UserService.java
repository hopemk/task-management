package com.taskmanagementservice.business.logic.service;

import com.taskmanagementservice.utils.requests.CreateUserRequest;
import com.taskmanagementservice.utils.requests.LoginRequest;
import com.taskmanagementservice.utils.responses.JwtResponse;
import com.taskmanagementservice.utils.responses.UserResponse;
import java.util.Locale;

public interface UserService {

    UserResponse create(CreateUserRequest createUserRequest, Locale locale);
    JwtResponse login(LoginRequest loginRequest, Locale locale);
}
