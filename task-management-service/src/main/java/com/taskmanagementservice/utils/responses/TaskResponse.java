package com.taskmanagementservice.utils.responses;

import com.taskmanagementservice.utils.dto.TaskDto;
import org.springframework.data.domain.Page;
import java.util.List;

public class TaskResponse extends CommonResponse{

    private TaskDto taskDto;
    private List<TaskDto> taskDtoList;
    private Page<TaskDto> taskDtoPage;

    public TaskDto getTaskDto() {
        return taskDto;
    }

    public void setTaskDto(TaskDto taskDto) {
        this.taskDto = taskDto;
    }

    public List<TaskDto> getTaskDtoList() {
        return taskDtoList;
    }

    public void setTaskDtoList(List<TaskDto> taskDtoList) {
        this.taskDtoList = taskDtoList;
    }

    public Page<TaskDto> getTaskDtoPage() {
        return taskDtoPage;
    }

    public void setTaskDtoPage(Page<TaskDto> taskDtoPage) {
        this.taskDtoPage = taskDtoPage;
    }

    @Override
    public String toString() {
        return "TaskResponse{" +
                "taskDto=" + taskDto +
                ", taskDtoList=" + taskDtoList +
                ", taskDtoPage=" + taskDtoPage +
                '}';
    }
}
