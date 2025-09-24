package com.taskmanagementservice.utils.enums;

public enum I18Code {
    MESSAGE_CREATE_USER_NULL_REQUEST("messages.create.user.null.request"),
    MESSAGE_CREATE_USER_NULL_OR_EMPTY_FIRST_NAME("messages.create.user.null.or.empty.first.name"),
    MESSAGE_CREATE_USER_NULL_OR_EMPTY_LAST_NAME("messages.create.user.null.or.empty.last.name"),
    MESSAGE_CREATE_USER_NULL_OR_EMPTY_USERNAME("messages.create.user.null.or.empty.username"),
    MESSAGE_CREATE_USER_NULL_OR_EMPTY_PHONE_NUMBER("messages.create.user.null.or.empty.phone.number"),

    MESSAGE_LOGIN_NULL_REQUEST("messages.login.null.request"),
    MESSAGE_LOGIN_NULL_OR_EMPTY_USERNAME("messages.login.null.or.empty.username"),
    MESSAGE_LOGIN_NULL_OR_EMPTY_PASSWORD("messages.login.null.or.empty.password"),
    MESSAGE_CREATE_USER_INVALID_REQUEST("messages.create.user.invalid.request"),
    MESSAGE_CREATE_USER_USER_EXIST("messages.create.user.user.exist"),
    MESSAGE_CREATE_USER_SUCCESSFUL("messages.create.user.successful"),
    MESSAGE_LOGIN_INVALID_REQUEST("messages.login.invalid.request"),

    // Task-related messages
    MESSAGE_CREATE_TASK_NULL_REQUEST("messages.create.task.null.request"),
    MESSAGE_CREATE_TASK_NULL_OR_EMPTY_TITLE("messages.create.task.null.or.empty.title"),
    MESSAGE_CREATE_TASK_NULL_OR_EMPTY_DESCRIPTION("messages.create.task.null.or.empty.description"),
    MESSAGE_EDIT_TASK_NULL_REQUEST("messages.edit.task.null.request"),
    MESSAGE_EDIT_TASK_NULL_OR_EMPTY_TITLE("messages.edit.task.null.or.empty.title"),
    MESSAGE_EDIT_TASK_NULL_OR_EMPTY_DESCRIPTION("messages.edit.task.null.or.empty.description"),
    MESSAGE_CREATE_TASK_INVALID_REQUEST("messages.create.task.invalid.request"),
    MESSAGE_CREATE_TASK_SUCCESSFUL("messages.create.task.successful"),
    MESSAGE_EDIT_TASK_INVALID_REQUEST("messages.edit.task.invalid.request"),
    MESSAGE_EDIT_TASK_SUCCESSFUL("messages.edit.task.successful"),
    MESSAGE_DELETE_TASK_SUCCESSFUL("messages.delete.task.successful"),
    MESSAGE_GET_TASK_SUCCESSFUL("messages.get.task.successful"),
    MESSAGE_TASK_NOT_FOUND("messages.task.not.found"),
    MESSAGE_TASK_OPERATION_NOT_IMPLEMENTED("messages.task.operation.not.implemented"),
    MESSAGE_EDIT_TASK_NULL_OR_EMPTY_TASK_STATUS("messages.edit.task.null.or.empty.task.status"),
    MESSAGE_EDIT_TASK_INVALID_TASK_STATUS("messages.edit.task.invalid.task.status"),;
    private String code;

    I18Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
