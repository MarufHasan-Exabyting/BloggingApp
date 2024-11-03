package com.example.UserDemo.dto;

import java.util.Date;

public class ResponseUserDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String userEmail;
    private Date created_at;
    private Date updated_at;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ResponseUserDTO(int userId, String firstName, String lastName, String userEmail, Date created_at, Date updated_at) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public ResponseUserDTO() {
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "ResponseUserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
