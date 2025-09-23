package com.taskmanagementservice.business.validator.impl;

import com.taskmanagementservice.business.logic.impl.MessageServiceImpl;
import com.taskmanagementservice.business.validator.service.TaskServiceValidator;
import com.taskmanagementservice.utils.dto.ValidatorDto;
import com.taskmanagementservice.utils.enums.I18Code;
import com.taskmanagementservice.utils.requests.CreateTaskRequest;
import com.taskmanagementservice.utils.requests.EditTaskRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TaskServiceValidatorImpl implements TaskServiceValidator {

    private final MessageServiceImpl messageService;

    public TaskServiceValidatorImpl(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @Override
    public ValidatorDto isRequestValidToCreateTask(CreateTaskRequest createTaskRequest, Locale locale) {
        List<String> errors = new ArrayList<>();

        if (createTaskRequest == null) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_CREATE_TASK_NULL_REQUEST.getCode(), new String[]{},
                    locale));
            return new ValidatorDto(false, errors);
        }

        if (createTaskRequest.getTitle() == null || createTaskRequest.getTitle().trim().isBlank()) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_CREATE_TASK_NULL_OR_EMPTY_TITLE.getCode(), new String[]{},
                    locale));
        }


        if (!errors.isEmpty()) {
            return new ValidatorDto(false, errors);
        }

        return new ValidatorDto(true, Collections.emptyList());
    }

    @Override
    public ValidatorDto isRequestValidToEditTask(EditTaskRequest editTaskRequest, Locale locale) {
        List<String> errors = new ArrayList<>();

        if (editTaskRequest == null) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_EDIT_TASK_NULL_REQUEST.getCode(), new String[]{},
                    locale));
            return new ValidatorDto(false, errors);
        }

        if (editTaskRequest.getTitle() == null || editTaskRequest.getTitle().trim().isBlank()) {
            errors.add(messageService.getMessage(I18Code.MESSAGE_EDIT_TASK_NULL_OR_EMPTY_TITLE.getCode(), new String[]{},
                    locale));
        }
        return new ValidatorDto(true, Collections.emptyList());
    }
}
