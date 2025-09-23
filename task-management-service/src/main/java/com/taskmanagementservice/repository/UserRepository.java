package com.taskmanagementservice.repository;

import com.microfinance.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Find a user by email.
     *
     * @param email The email to search for
     * @return An Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user with the given email exists.
     *
     * @param email The email to check
     * @return True if a user with the email exists, false otherwise
     */
    Boolean existsByEmail(String email);
}