package com.taskmanagementservice.utils.enums;

public enum I18Code {
    MESSAGE_CREATE_USER_NULL_REQUEST("messages.create.user.null.request"),
    MESSAGE_CREATE_USER_NULL_OR_EMPTY_FIRST_NAME("messages.create.user.null.or.empty.first.name"),
    MESSAGE_CREATE_USER_NULL_OR_EMPTY_LAST_NAME("messages.create.user.null.or.empty.last.name"),
    MESSAGE_CREATE_USER_NULL_OR_EMPTY_USERNAME("messages.create.user.null.or.empty.username"),
    MESSAGE_CREATE_USER_NULL_OR_EMPTY_PHONE_NUMBER("messages.create.user.null.or.empty.phone.number"),

    MESSAGE_LOGIN_NULL_REQUEST("messages.login.null.request"),
    MESSAGE_LOGIN_NULL_OR_EMPTY_USERNAME("messages.login.null.or.empty.username"),
    MESSAGE_LOGIN_NULL_OR_EMPTY_PASSWORD("messages.login.null.or.empty.password");
    private String code;

    I18Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
