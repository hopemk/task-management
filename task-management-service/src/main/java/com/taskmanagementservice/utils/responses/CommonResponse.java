package com.taskmanagementservice.utils.responses;

import java.util.List;

public class CommonResponse {

    private int statusCode;
    private String message;
    private Boolean success;
    private List<String> errorMessages;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", errorMessages=" + errorMessages +
                '}';
    }
}
