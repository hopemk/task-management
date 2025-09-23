package com.taskmanagementservice.config;

import com.taskmanagementservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        initRoles();
    }

    private void initRoles() {

        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        for (String roleName : roles) {
            if (!roleRepository.existsByName(roleName)) {
                roleRepository.save(new com.taskmanagementservice.model.Role(roleName, ""));
            }
        }
    }
}