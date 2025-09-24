package com.taskmanagementservice.utils.auth;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthDetailsUtil {

    public static String getLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
