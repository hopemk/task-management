package com.taskmanagementservice.controller;

import com.taskmanagementservice.utils.responses.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<CommonResponse> handleBadCredentials(BadCredentialsException ex) {
        CommonResponse response = new CommonResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        response.setSuccess(false);
        response.setMessage("Invalid username or password");
        response.setErrorMessages(List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<CommonResponse> handleAuthentication(AuthenticationException ex) {
        CommonResponse response = new CommonResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        response.setSuccess(false);
        response.setMessage("Authentication failed");
        response.setErrorMessages(List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGeneralException(Exception ex) {
        CommonResponse response = new CommonResponse();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setSuccess(false);
        response.setMessage("An unexpected error occurred");
        response.setErrorMessages(List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
