package com.taskmanagementservice.utils.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidatorDto {

    private boolean isValid;
    private List<String> errorMessages;

    public ValidatorDto(boolean isValid, List<String> errorMessages) {
        this.isValid = isValid;
        this.errorMessages = errorMessages;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        return "ValidatorDto{" +
                "isValid=" + isValid +
                ", errorMessages=" + errorMessages +
                '}';
    }
}
