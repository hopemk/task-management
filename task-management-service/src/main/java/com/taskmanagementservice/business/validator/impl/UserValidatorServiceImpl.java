package com.taskmanagementservice.business.validator.impl;

import com.taskmanagementservice.business.logic.impl.MessageServiceImpl;
import com.taskmanagementservice.business.validator.service.UserValidatorService;
import com.taskmanagementservice.utils.dto.ValidatorDto;
import com.taskmanagementservice.utils.enums.I18Code;
import com.taskmanagementservice.utils.requests.CreateUserRequest;
import com.taskmanagementservice.utils.requests.LoginRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class UserValidatorServiceImpl implements UserValidatorService {

    private final MessageServiceImpl messageService;

    public UserValidatorServiceImpl(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @Override
    public ValidatorDto isRequestValidToCreateUser(CreateUserRequest createUserRequest, Locale locale) {

        List<String> errors = new ArrayList<>();

        if (createUserRequest == null) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_CREATE_USER_NULL_REQUEST.getCode(), new String[]{},
                    locale));
            return new ValidatorDto(false, errors);
        }

        if (createUserRequest.getFirstName() == null || createUserRequest.getFirstName().trim().isBlank()) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_CREATE_USER_NULL_OR_EMPTY_FIRST_NAME.getCode(), new String[]{},
                    locale));
        }

        if (createUserRequest.getLastName() == null || createUserRequest.getLastName().trim().isBlank()) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_CREATE_USER_NULL_OR_EMPTY_LAST_NAME.getCode(), new String[]{},
                    locale));
        }

        if (createUserRequest.getUsername() == null || createUserRequest.getUsername().trim().isBlank()) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_CREATE_USER_NULL_OR_EMPTY_USERNAME.getCode(), new String[]{},
                    locale));
        }

        if (createUserRequest.getPhoneNumber() == null || createUserRequest.getPhoneNumber().trim().isBlank()) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_CREATE_USER_NULL_OR_EMPTY_PHONE_NUMBER.getCode(), new String[]{},
                    locale));
        }

        if (!errors.isEmpty()) {
            return new ValidatorDto(false, errors);
        }

        return new ValidatorDto(true, Collections.emptyList());
    }

    @Override
    public ValidatorDto isRequestValidToLogin(LoginRequest loginRequest, Locale locale) {
        List<String> errors = new ArrayList<>();

        if (loginRequest == null) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_LOGIN_NULL_REQUEST.getCode(), new String[]{},
                    locale));
            return new ValidatorDto(false, errors);
        }

        if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isBlank()) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_LOGIN_NULL_OR_EMPTY_USERNAME.getCode(), new String[]{},
                    locale));
        }

        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isBlank()) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_LOGIN_NULL_OR_EMPTY_PASSWORD.getCode(), new String[]{},
                    locale));
        }

        if (!errors.isEmpty()) {
            return new ValidatorDto(false, errors);
        }

        return new ValidatorDto(true, Collections.emptyList());
    }
}
