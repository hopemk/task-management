package com.taskmanagementservice.business.validator.service;

import com.taskmanagementservice.utils.dto.ValidatorDto;
import com.taskmanagementservice.utils.requests.CreateUserRequest;
import com.taskmanagementservice.utils.requests.LoginRequest;

import java.util.Locale;

public interface UserValidatorService {

    ValidatorDto isRequestValidToCreateUser(CreateUserRequest createUserRequest, Locale locale);
    ValidatorDto isRequestValidToLogin(LoginRequest loginRequest, Locale locale);
}
