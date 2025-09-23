package com.taskmanagementservice.business.logic.impl;

import com.taskmanagementservice.business.logic.service.MessageService;
import com.taskmanagementservice.business.logic.service.TaskService;
import com.taskmanagementservice.business.validator.service.TaskServiceValidator;
import com.taskmanagementservice.model.EntityStatus;
import com.taskmanagementservice.utils.dto.TaskDto;
import com.taskmanagementservice.utils.dto.ValidatorDto;
import com.taskmanagementservice.utils.enums.I18Code;
import com.taskmanagementservice.utils.requests.CreateTaskRequest;
import com.taskmanagementservice.utils.requests.EditTaskRequest;
import com.taskmanagementservice.utils.responses.TaskResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class TaskServiceImpl implements TaskService {

    private final MessageService messageService;
    private final TaskServiceValidator taskServiceValidator;
    private final ModelMapper modelMapper;
    private final com.taskmanagementservice.repository.TaskRepository taskRepository;

    public TaskServiceImpl(MessageService messageService,
                           TaskServiceValidator taskServiceValidator,
                           ModelMapper modelMapper,
                           com.taskmanagementservice.repository.TaskRepository taskRepository) {
        this.messageService = messageService;
        this.taskServiceValidator = taskServiceValidator;
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest createTaskRequest, Locale locale) {
        String message;

        ValidatorDto validatorDto = taskServiceValidator.isRequestValidToCreateTask(createTaskRequest, locale);

        if (!validatorDto.isValid()) {
            message = messageService.getMessage(I18Code.MESSAGE_CREATE_TASK_INVALID_REQUEST.getCode(), new String[]{},
                    locale);
            return buildTaskResponse(message, 400, false, null, null, null, validatorDto.getErrorMessages());
        }
        
        com.taskmanagementservice.model.Task task = modelMapper.map(createTaskRequest, com.taskmanagementservice.model.Task.class);
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext() != null &&
                org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication() != null ?
                org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName() :
                "system";
        task.setCreatedBy(username);
        com.taskmanagementservice.model.Task saved = taskRepository.save(task);

        TaskDto taskDto = modelMapper.map(saved, TaskDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_CREATE_TASK_SUCCESSFUL.getCode(), new String[]{},
                locale);
        return buildTaskResponse(message, 201, true, taskDto, null, null, null);
    }

    @Override
    public TaskResponse editTask(Long taskId, EditTaskRequest editTaskRequest, Locale locale) {
        String message;

        ValidatorDto validatorDto = taskServiceValidator.isRequestValidToEditTask(editTaskRequest, locale);
        if (!validatorDto.isValid()) {
            message = messageService.getMessage(I18Code.MESSAGE_EDIT_TASK_INVALID_REQUEST.getCode(), new String[]{}, locale);
            return buildTaskResponse(message, 400, false, null, null, null, validatorDto.getErrorMessages());
        }

        Optional<com.taskmanagementservice.model.Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty() || optionalTask.get().getEntityStatus() == EntityStatus.DELETED) {
            message = messageService.getMessage(I18Code.MESSAGE_TASK_NOT_FOUND.getCode(), new String[]{String.valueOf(taskId)}, locale);
            return buildTaskResponse(message, 404, false, null, null, null, null);
        }

        com.taskmanagementservice.model.Task task = optionalTask.get();
        task.setTitle(editTaskRequest.getTitle());
        task.setDescription(editTaskRequest.getDescription());
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext() != null &&
                org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication() != null ?
                org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName() :
                "system";
        task.setUpdatedBy(username);

        com.taskmanagementservice.model.Task saved = taskRepository.save(task);
        TaskDto taskDto = modelMapper.map(saved, TaskDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_EDIT_TASK_SUCCESSFUL.getCode(), new String[]{}, locale);
        return buildTaskResponse(message, 200, true, taskDto, null, null, null);
    }

    @Override
    public TaskResponse deleteTask(Long taskId, Locale locale) {
        String message;
        Optional<com.taskmanagementservice.model.Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty() || optionalTask.get().getEntityStatus() == EntityStatus.DELETED) {
            message = messageService.getMessage(I18Code.MESSAGE_TASK_NOT_FOUND.getCode(), new String[]{String.valueOf(taskId)}, locale);
            return buildTaskResponse(message, 404, false, null, null, null, null);
        }

        com.taskmanagementservice.model.Task task = optionalTask.get();
        task.setEntityStatus(EntityStatus.DELETED);
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext() != null &&
                org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication() != null ?
                org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName() :
                "system";
        task.setUpdatedBy(username);
        com.taskmanagementservice.model.Task saved = taskRepository.save(task);

        TaskDto taskDto = modelMapper.map(saved, TaskDto.class);
        message = messageService.getMessage(I18Code.MESSAGE_DELETE_TASK_SUCCESSFUL.getCode(), new String[]{}, locale);
        return buildTaskResponse(message, 200, true, taskDto, null, null, null);
    }

    @Override
    public TaskResponse getTaskById(Long taskId, Locale locale) {
        String message;
        Optional<com.taskmanagementservice.model.Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty() || optionalTask.get().getEntityStatus() == EntityStatus.DELETED) {
            message = messageService.getMessage(I18Code.MESSAGE_TASK_NOT_FOUND.getCode(), new String[]{String.valueOf(taskId)}, locale);
            return buildTaskResponse(message, 404, false, null, null, null, null);
        }

        TaskDto taskDto = modelMapper.map(optionalTask.get(), TaskDto.class);
        message = messageService.getMessage(I18Code.MESSAGE_GET_TASK_SUCCESSFUL.getCode(), new String[]{}, locale);
        return buildTaskResponse(message, 200, true, taskDto, null, null, null);
    }

    @Override
    public TaskResponse getAllTasks(Locale locale) {
        List<com.taskmanagementservice.model.Task> tasks = taskRepository.findByEntityStatusNot(EntityStatus.DELETED);

        if (tasks.isEmpty()) {
            String message = messageService.getMessage(I18Code.MESSAGE_TASK_NOT_FOUND.getCode(), new String[]{}, locale);
            return buildTaskResponse(message, 404, false, null, null, null, null);
        }

        List<TaskDto> taskDtoListReturned = modelMapper.map(tasks, new TypeToken<List<TaskDto>>(){}.getType());

        String message = messageService.getMessage(I18Code.MESSAGE_GET_TASK_SUCCESSFUL.getCode(), new String[]{}, locale);
        return buildTaskResponse(message, 200, true, null, taskDtoListReturned, null, null);
    }

    private TaskResponse buildTaskResponse(String message, int statusCode, boolean success,
                                           TaskDto taskDto, List<TaskDto> taskDtoList,
                                           org.springframework.data.domain.Page<TaskDto> taskDtoPage,
                                           List<String> errorMessages) {
        TaskResponse response = new TaskResponse();
        response.setMessage(message);
        response.setStatusCode(statusCode);
        response.setSuccess(success);
        response.setTaskDto(taskDto);
        response.setTaskDtoList(taskDtoList);
        response.setTaskDtoPage(taskDtoPage);
        response.setErrorMessages(errorMessages);
        return response;
    }
}
