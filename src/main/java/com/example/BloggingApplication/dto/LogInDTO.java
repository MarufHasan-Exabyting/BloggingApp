package com.example.BloggingApplication.dto;

import jakarta.validation.constraints.NotEmpty;

public class LogInDTO {
    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;

    public LogInDTO() {
    }

    public LogInDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
