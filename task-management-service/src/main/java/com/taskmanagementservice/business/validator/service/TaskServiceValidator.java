package com.taskmanagementservice.business.validator.service;

import com.taskmanagementservice.utils.dto.ValidatorDto;
import com.taskmanagementservice.utils.requests.CreateTaskRequest;
import com.taskmanagementservice.utils.requests.EditTaskRequest;
import java.util.Locale;

public interface TaskServiceValidator {

    ValidatorDto isRequestValidToCreateTask(CreateTaskRequest createTaskRequest, Locale locale);
    ValidatorDto isRequestValidToEditTask(EditTaskRequest editTaskRequest, Locale locale);
}
