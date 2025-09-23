package com.taskmanagementservice.business.logic.service;

import com.taskmanagementservice.utils.requests.CreateTaskRequest;
import com.taskmanagementservice.utils.requests.EditTaskRequest;
import com.taskmanagementservice.utils.responses.TaskResponse;
import java.util.Locale;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest createTaskRequest, Locale locale);
    TaskResponse editTask(Long taskId, EditTaskRequest editTaskRequest, Locale locale);
    TaskResponse deleteTask(Long taskId, Locale locale);
    TaskResponse getTaskById(Long taskId, Locale locale);
    TaskResponse getAllTasks(Locale locale);
}
