package com.taskmanagementservice.business.logic.service;

import java.util.Locale;

public interface MessageService {
    String getMessage(String propertyName, String[] placeholders, Locale locale);
    String getMessage(String propertyName, Locale locale);
    String getMessage(String propertyName, String[] placeholders, String defaultMessage, Locale locale);
}
