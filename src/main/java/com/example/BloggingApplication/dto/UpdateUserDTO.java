package com.example.BloggingApplication.dto;

import jakarta.validation.constraints.*;

public class UpdateUserDTO {

    //Required Fields for UpdateUserDTO
    @Positive
    private int userId;

    @NotEmpty(message = "User Name is required")
    @Pattern(regexp="^[A-Za-z0-9_]*$", message = "Invalid Input")
    private String userName;

    //Optional Fields
    //Fields which can be updated

    private String firstName;

    private String lastName;

    private String password;



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UpdateUserDTO() {
    }

    public UpdateUserDTO(int userId, String firstName, String lastName, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public @NotEmpty(message = "User Name is required") String getUserName() {
        return userName;
    }

    public void setUserName(@NotEmpty(message = "User Name is required") String userName) {
        this.userName = userName;
    }
}
