package com.taskmanagementservice.business.logic.impl;

import com.taskmanagementservice.business.logic.service.MessageService;
import org.springframework.context.MessageSource;
import java.util.Locale;

public class MessageServiceImpl implements MessageService {

    private MessageSource messageSource;

    public MessageServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String propertyName, String[] placeholders, Locale locale) {
        return messageSource.getMessage(propertyName, placeholders, locale);
    }

    public String getMessage(String propertyName, Locale locale) {
        return messageSource.getMessage(propertyName, new String[] {}, locale);
    }

    @Override
    public String getMessage(String propertyName, String[] placeholders, String defaultMessage, Locale locale) {
        return messageSource.getMessage(propertyName, placeholders, defaultMessage, locale);
    }
}
