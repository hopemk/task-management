package com.taskmanagementservice.utils.requests;

public class CreateUserRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;

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
    @Override
    public String toString() {
        return "CreateUserRequest [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
                + ", phoneNumber=" + phoneNumber + "]";
    }
}
