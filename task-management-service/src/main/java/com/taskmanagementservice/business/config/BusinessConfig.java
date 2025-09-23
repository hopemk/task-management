package com.taskmanagementservice.business.config;

import com.taskmanagementservice.business.logic.impl.TaskServiceImpl;
import com.taskmanagementservice.business.logic.impl.UserServiceImpl;
import com.taskmanagementservice.business.logic.service.MessageService;
import com.taskmanagementservice.business.logic.service.TaskService;
import com.taskmanagementservice.business.logic.service.UserService;
import com.taskmanagementservice.business.validator.impl.TaskServiceValidatorImpl;
import com.taskmanagementservice.business.validator.impl.UserValidatorServiceImpl;
import com.taskmanagementservice.business.validator.service.TaskServiceValidator;
import com.taskmanagementservice.business.validator.service.UserValidatorService;
import com.taskmanagementservice.jwt.JwtUtils;
import com.taskmanagementservice.repository.RoleRepository;
import com.taskmanagementservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BusinessConfig {

    @Bean
    public UserValidatorService userValidatorService(MessageService messageService) {
        return new UserValidatorServiceImpl((com.taskmanagementservice.business.logic.impl.MessageServiceImpl) messageService);
    }

    @Bean
    public UserService userService(MessageService messageService,
                                   UserRepository userRepository,
                                   UserValidatorService userValidatorService,
                                   RoleRepository roleRepository,
                                   ModelMapper modelMapper,
                                   PasswordEncoder passwordEncoder,
                                   AuthenticationManager authenticationManager,
                                   JwtUtils jwtUtils) {
        return new UserServiceImpl(
                messageService,
                userRepository,
                userValidatorService,
                passwordEncoder,
                roleRepository,
                modelMapper,
                passwordEncoder,
                authenticationManager,
                jwtUtils
        );
    }

    @Bean
    public TaskServiceValidator taskServiceValidator(MessageService messageService) {
        return new TaskServiceValidatorImpl((com.taskmanagementservice.business.logic.impl.MessageServiceImpl) messageService);
    }

    @Bean
    public TaskService taskService(MessageService messageService,
                                   TaskServiceValidator taskServiceValidator,
                                   ModelMapper modelMapper,
                                   com.taskmanagementservice.repository.TaskRepository taskRepository) {
        return new TaskServiceImpl(messageService, taskServiceValidator, modelMapper, taskRepository);
    }
}
