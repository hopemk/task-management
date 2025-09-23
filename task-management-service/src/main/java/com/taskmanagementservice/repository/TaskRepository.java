package com.taskmanagementservice.repository;

import com.taskmanagementservice.model.EntityStatus;
import com.taskmanagementservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByEntityStatusNot(EntityStatus entityStatus);
}
