package com.taskmanagementservice.business.logic.impl;

import com.taskmanagementservice.business.logic.service.MessageService;
import com.taskmanagementservice.business.logic.service.UserService;
import com.taskmanagementservice.business.validator.service.UserValidatorService;
import com.taskmanagementservice.jwt.JwtUtils;
import com.taskmanagementservice.model.EntityStatus;
import com.taskmanagementservice.model.Role;
import com.taskmanagementservice.model.User;
import com.taskmanagementservice.repository.RoleRepository;
import com.taskmanagementservice.repository.UserRepository;
import com.taskmanagementservice.utils.auth.AuthDetailsUtil;
import com.taskmanagementservice.utils.dto.JwtDto;
import com.taskmanagementservice.utils.dto.UserDto;
import com.taskmanagementservice.utils.dto.ValidatorDto;
import com.taskmanagementservice.utils.enums.I18Code;
import com.taskmanagementservice.utils.requests.CreateUserRequest;
import com.taskmanagementservice.utils.requests.LoginRequest;
import com.taskmanagementservice.utils.responses.JwtResponse;
import com.taskmanagementservice.utils.responses.UserResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final MessageService messageService;
    private final UserRepository userRepository;
    private final UserValidatorService userValidatorService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(MessageService messageService, UserRepository userRepository, UserValidatorService userValidatorService, PasswordEncoder encoder, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.messageService = messageService;
        this.userRepository = userRepository;
        this.userValidatorService = userValidatorService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserResponse create(CreateUserRequest createUserRequest, Locale locale) {

        String message = "";

        ValidatorDto validatorDto = userValidatorService.isRequestValidToCreateUser(createUserRequest, locale);

        if (!validatorDto.isValid()) {
            message = messageService.getMessage(I18Code.MESSAGE_CREATE_USER_INVALID_REQUEST.getCode(), new String[]{},
                    locale);
            return buildUserResponse(message, 400,false, null, null, null, validatorDto.getErrorMessages());
        }
        if (userRepository.existsByUsernameAndStatusNot(createUserRequest.getUsername(), EntityStatus.DELETED)) {
            message = messageService.getMessage(I18Code.MESSAGE_CREATE_USER_USER_EXIST.getCode(), new String[]{createUserRequest.getUsername()},
                    locale);
            return buildUserResponse(message, 400, false,null, null, null, null);
        }

        User userSaved = userRepository.save(buildUser(createUserRequest));

        log.info("User created successfully with username: {}", userSaved.getUsername());
        
        UserDto userDto = modelMapper.map(userSaved, UserDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_CREATE_USER_SUCCESSFUL.getCode(), new String[]{createUserRequest.getUsername()},
                locale);
        return buildUserResponse(message, 201, true, userDto, null, null, null);
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest, Locale locale) {

        String message = "";

        ValidatorDto validatorDto = userValidatorService.isRequestValidToLogin(loginRequest, locale);

        if (!validatorDto.isValid()) {
            message = messageService.getMessage(I18Code.MESSAGE_LOGIN_INVALID_REQUEST.getCode(), new String[]{},
                    locale);
            return buildJwtResponse(message, 400, false, null, validatorDto.getErrorMessages());
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            log.info("User logged in successfully with username: {}", authentication.getName());

            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            JwtDto jwtDto = buildJwtDto(userDetails, jwt);
            message = messageService.getMessage(I18Code.MESSAGE_LOGIN_INVALID_REQUEST.getCode(), new String[]{},
                    locale);
            return buildJwtResponse(message, 200, true, jwtDto, null);
        } catch (AuthenticationException ex) {
            log.warn("Authentication failed for username={}: {}", loginRequest.getUsername(), ex.getClass().getSimpleName());
            message = messageService.getMessage(I18Code.MESSAGE_LOGIN_INVALID_REQUEST.getCode(), new String[]{},
                    locale);
            return buildJwtResponse(message, 401, false, null, null);
        } catch (Exception ex) {
            log.error("Unexpected error during login for username={}", loginRequest.getUsername(), ex);
            message = messageService.getMessage(I18Code.MESSAGE_LOGIN_INVALID_REQUEST.getCode(), new String[]{},
                    locale);
            return buildJwtResponse(message, 500, false, null, null);
        }
    }

    private User buildUser(CreateUserRequest createUserRequest){

        User user = modelMapper.map(createUserRequest, User.class);
        user.setCreatedBy(AuthDetailsUtil.getLoggedInUsername());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        Optional<Role> role = roleRepository.findByName("ROLE_USER");

        if (role.isPresent()) {
            user.setRoles(Set.of(role.get()));
        }

        return user;
    }

    private UserResponse buildUserResponse(String message, int statusCode,boolean success, UserDto userDto, List<UserDto> userDtoList,
                                           Page<UserDto> userDtoPage, List<String> errorMessages) {

        UserResponse userResponse = new UserResponse();

        userResponse.setMessage(message);
        userResponse.setStatusCode(statusCode);
        userResponse.setUserDto(userDto);
        userResponse.setUserDtoList(userDtoList);
        userResponse.setUserDtoPage(userDtoPage);
        userResponse.setErrorMessages(errorMessages);
        userResponse.setSuccess(success);

        return userResponse;
    }

    private JwtResponse buildJwtResponse(String message, int statusCode, boolean success, JwtDto jwtDto, List<String> errorMessages) {

        JwtResponse jwtResponse = new JwtResponse();

        jwtResponse.setMessage(message);
        jwtResponse.setStatusCode(statusCode);
        jwtResponse.setSuccess(success);
        jwtResponse.setJwtDto(jwtDto);
        jwtResponse.setErrorMessages(errorMessages);

        return jwtResponse;
    }

    private JwtDto buildJwtDto(UserDetailsImpl userDetails, String jwt){

        JwtDto jwtDto = new JwtDto();

        jwtDto.setId(userDetails.getId());
        jwtDto.setFirstName(userDetails.getFirstName());
        jwtDto.setLastName(userDetails.getLastName());
        jwtDto.setUsername(userDetails.getUsername());
        jwtDto.setPhoneNumber(userDetails.getPhoneNumber());
        jwtDto.setRoles(userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList()));
        jwtDto.setToken(jwt);

        return jwtDto;
    }
}
