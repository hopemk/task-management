package com.taskmanagementservice.utils.dto;

import java.util.List;

public class JwtDto {

    private String token;
    private String type = "Bearer";
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private List<String> roles;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    @Override
    public String toString() {
        return "JwtDto [token=" + token + ", type=" + type + ", id=" + id + ", firstName=" + firstName + ", lastName="
                + lastName + ", email=" + username + ", phoneNumber=" + phoneNumber + ", roles=" + roles + "]";
    }
}
