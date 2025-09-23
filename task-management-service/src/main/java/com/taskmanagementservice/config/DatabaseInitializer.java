package com.taskmanagementservice.config;

import com.taskmanagementservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

/**
 * Component to initialize the database with required data on application startup.
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Initializes the database with required roles.
     *
     * @param args Command line arguments
     */
    @Override
    public void run(String... args) {

        //initRoles();
    }

    /**
     * Initializes the roles in the database if they don't exist.
     */
//    private void initRoles() {
//        Arrays.stream(ERole.values()).forEach(role -> {
//            if (!roleRepository.findByName(role).isPresent()) {
//                Role newRole = new Role(role, "Role for " + role.name());
//                roleRepository.save(newRole);
//            }
//        });
//    }
}