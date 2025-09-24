package com.taskmanagementservice.business.logic.impl;

import com.taskmanagementservice.business.logic.service.MessageService;
import com.taskmanagementservice.business.logic.service.TaskService;
import com.taskmanagementservice.business.validator.service.TaskServiceValidator;
import com.taskmanagementservice.model.EntityStatus;
import com.taskmanagementservice.model.Task;
import com.taskmanagementservice.model.TaskStatus;
import com.taskmanagementservice.repository.TaskRepository;
import com.taskmanagementservice.utils.auth.AuthDetailsUtil;
import com.taskmanagementservice.utils.dto.TaskDto;
import com.taskmanagementservice.utils.dto.ValidatorDto;
import com.taskmanagementservice.utils.enums.I18Code;
import com.taskmanagementservice.utils.requests.CreateTaskRequest;
import com.taskmanagementservice.utils.requests.EditTaskRequest;
import com.taskmanagementservice.utils.responses.TaskResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class TaskServiceImpl implements TaskService {

    private final MessageService messageService;
    private final TaskServiceValidator taskServiceValidator;
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;
    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    public TaskServiceImpl(MessageService messageService,
                           TaskServiceValidator taskServiceValidator,
                           ModelMapper modelMapper,
                           TaskRepository taskRepository) {
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
            return buildTaskResponse(message, 400, false, null, null,
                    null, validatorDto.getErrorMessages());
        }
        
        Task taskToBeSaved = modelMapper.map(createTaskRequest, Task.class);

        taskToBeSaved.setCreatedBy(AuthDetailsUtil.getLoggedInUsername());

        Task taskSaved = taskRepository.save(taskToBeSaved);
        log.info("Task created successfully with title: {}", taskSaved.getTitle());

        TaskDto taskDto = modelMapper.map(taskSaved, TaskDto.class);

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

        Optional<Task> taskRetrieved = taskRepository.
                findByIdAndCreatedByAndEntityStatusNot(taskId, AuthDetailsUtil.getLoggedInUsername(), EntityStatus.DELETED);
        if (taskRetrieved.isEmpty() || taskRetrieved.get().getEntityStatus() == EntityStatus.DELETED) {
            message = messageService.getMessage(I18Code.MESSAGE_TASK_NOT_FOUND.getCode(), new String[]{String.valueOf(taskId)}, locale);
            return buildTaskResponse(message, 400, false, null,
                    null, null, null);
        }

        Task taskEdited = taskRepository.save(buildTaskForUpdate(taskRetrieved.get(), editTaskRequest));

        log.info("Task edited successfully with title: {}", taskEdited.getTitle());

        TaskDto taskDto = modelMapper.map(taskEdited, TaskDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_EDIT_TASK_SUCCESSFUL.getCode(), new String[]{}, locale);
        return buildTaskResponse(message, 200, true, taskDto, null,
                null, null);
    }

    @Override
    public TaskResponse deleteTask(Long taskId, Locale locale) {

        String message;

        Optional<Task> taskRetrieved = taskRepository.findByIdAndCreatedByAndEntityStatusNot(taskId, AuthDetailsUtil.getLoggedInUsername(), EntityStatus.DELETED);

        if (taskRetrieved.isEmpty()) {
            message = messageService.getMessage(I18Code.MESSAGE_TASK_NOT_FOUND.getCode(), new String[]{String.valueOf(taskId)}, locale);
            return buildTaskResponse(message, 400, false, null, null, null, null);
        }

        Task taskDeleted = taskRepository.save(buildTaskToDelete(taskRetrieved.get()));

        TaskDto taskDeletedDto = modelMapper.map(taskDeleted, TaskDto.class);
        message = messageService.getMessage(I18Code.MESSAGE_DELETE_TASK_SUCCESSFUL.getCode(), new String[]{}, locale);
        return buildTaskResponse(message, 200, true, taskDeletedDto, null, null, null);
    }

    @Override
    public TaskResponse getTaskById(Long taskId, Locale locale) {

        String message;

        Optional<Task> optionalTask = taskRepository
                .findByIdAndCreatedByAndEntityStatusNot(taskId, AuthDetailsUtil.getLoggedInUsername(), EntityStatus.DELETED);

        if (optionalTask.isEmpty()) {
            message = messageService.getMessage(I18Code.MESSAGE_TASK_NOT_FOUND.getCode(), new String[]{String.valueOf(taskId)}, locale);
            return buildTaskResponse(message, 404, false, null, null, null, null);
        }

        TaskDto taskDto = modelMapper.map(optionalTask.get(), TaskDto.class);
        message = messageService.getMessage(I18Code.MESSAGE_GET_TASK_SUCCESSFUL.getCode(), new String[]{}, locale);
        return buildTaskResponse(message, 200, true, taskDto, null, null, null);
    }

    @Override
    public TaskResponse getAllTasks(Locale locale) {
        List<Task> tasks = taskRepository.findByCreatedByAndEntityStatusNot(AuthDetailsUtil.getLoggedInUsername(), EntityStatus.DELETED);

        if (tasks.isEmpty()) {
            String message = messageService.getMessage(I18Code.MESSAGE_TASK_NOT_FOUND.getCode(), new String[]{}, locale);
            return buildTaskResponse(message, 404, false, null, null, null, null);
        }

        List<TaskDto> taskDtoListReturned = modelMapper.map(tasks, new TypeToken<List<TaskDto>>(){}.getType());

        String message = messageService.getMessage(I18Code.MESSAGE_GET_TASK_SUCCESSFUL.getCode(), new String[]{}, locale);
        return buildTaskResponse(message, 200, true, null, taskDtoListReturned, null, null);
    }

    private Task buildTaskForUpdate(Task task, EditTaskRequest editTaskRequest) {

        task.setTitle(editTaskRequest.getTitle());
        task.setDescription(editTaskRequest.getDescription());
        task.setTaskStatus(TaskStatus.valueOf(editTaskRequest.getTaskStatus()));
        task.setUpdatedBy(AuthDetailsUtil.getLoggedInUsername());

        return task;
    }

    private Task buildTaskToDelete(Task task) {

        task.setEntityStatus(EntityStatus.DELETED);
        String username = AuthDetailsUtil.getLoggedInUsername();
        task.setTitle(task.getTitle() + " " + LocalDateTime.now().toString());
        task.setUpdatedBy(username);

        return task;
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
