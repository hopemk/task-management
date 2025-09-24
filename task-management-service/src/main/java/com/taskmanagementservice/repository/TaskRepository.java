package com.taskmanagementservice.repository;

import com.taskmanagementservice.model.EntityStatus;
import com.taskmanagementservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndCreatedByAndEntityStatusNot(Long taskId, String loggedInUsername, EntityStatus entityStatus);
    List<Task> findByCreatedByAndEntityStatusNot(String loggedInUsername, EntityStatus entityStatus);
}
