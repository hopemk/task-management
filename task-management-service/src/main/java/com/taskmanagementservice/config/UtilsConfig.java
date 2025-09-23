package com.taskmanagementservice.config;

import com.taskmanagementservice.business.logic.impl.MessageServiceImpl;
import com.taskmanagementservice.business.logic.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UtilsConfig {

    @Bean(name = "customMessageSource")
    public MessageSource customMessageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public MessageService messageService() {
        return new MessageServiceImpl(customMessageSource());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate restTemplate () {
        return new RestTemplate();
    }

}
