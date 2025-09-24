package com.taskmanagementservice.utils.enums;

public enum TaskManagementServiceRoles {

    USER("USER", "Role for user"), ADMIN("ADMIN", "Role for admin");

    private String roleName;
    private String description;

    TaskManagementServiceRoles(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }
}
