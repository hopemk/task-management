package com.taskmanagementservice.utils.requests;

public class EditTaskRequest {

    private String title;
    private String description;
    private String taskStatus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "EditTaskRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus='" + taskStatus + '\'' +
                '}';
    }
}
