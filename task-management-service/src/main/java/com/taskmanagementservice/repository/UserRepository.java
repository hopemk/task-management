package com.taskmanagementservice.repository;

import com.taskmanagementservice.model.EntityStatus;
import com.taskmanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndStatusNot(String username, EntityStatus entityStatus);
    Boolean existsByUsernameAndStatusNot(String username, EntityStatus entityStatus);
}