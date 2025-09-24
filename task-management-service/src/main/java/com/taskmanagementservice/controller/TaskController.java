package com.taskmanagementservice.controller;

import com.taskmanagementservice.business.logic.service.TaskService;
import com.taskmanagementservice.utils.requests.CreateTaskRequest;
import com.taskmanagementservice.utils.requests.EditTaskRequest;
import com.taskmanagementservice.utils.responses.TaskResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;

@CrossOrigin
@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasRole(T(com.taskmanagementservice.utils.enums.TaskManagementServiceRoles).USER.toString())")
    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody CreateTaskRequest request) {
        TaskResponse response = taskService.createTask(request, Locale.ENGLISH);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PreAuthorize("hasRole(T(com.taskmanagementservice.utils.enums.TaskManagementServiceRoles).USER.toString())")
    @PutMapping("/{taskId}")
    public ResponseEntity<?> editTask(@PathVariable("taskId") Long taskId,
                                      @Valid @RequestBody EditTaskRequest request) {
        TaskResponse response = taskService.editTask(taskId, request, Locale.ENGLISH);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PreAuthorize("hasRole(T(com.taskmanagementservice.utils.enums.TaskManagementServiceRoles).USER.toString())")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") Long taskId) {
        TaskResponse response = taskService.deleteTask(taskId, Locale.ENGLISH);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PreAuthorize("hasRole(T(com.taskmanagementservice.utils.enums.TaskManagementServiceRoles).USER.toString())")
    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable("taskId") Long taskId) {
        TaskResponse response = taskService.getTaskById(taskId, Locale.ENGLISH);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PreAuthorize("hasRole(T(com.taskmanagementservice.utils.enums.TaskManagementServiceRoles).USER.toString())")
    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        TaskResponse response = taskService.getAllTasks(Locale.ENGLISH);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
