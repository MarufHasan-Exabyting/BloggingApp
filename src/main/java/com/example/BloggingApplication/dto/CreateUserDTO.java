package com.example.BloggingApplication.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



public class CreateUserDTO {
    @NotEmpty(message = "First Name is required")
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    private String lastName;


    @NotEmpty(message = "UserName is required")
    @Pattern(regexp="^[A-Za-z0-9_]*$", message = "Invalid Input")
    private String userName;

    @NotEmpty(message = "password is required")
    @Size(min = 6,max = 20,message = "Length of password should be between 6-20")
    private String password;

    @Email
    @NotEmpty(message = "User Email can not be empty")
    private String userEmail;

    public CreateUserDTO() {
    }

    public CreateUserDTO(String firstName, String lastName, String userName, String password, String userEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.userEmail = userEmail;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public @NotEmpty(message = "UserName is required") String getUserName() {
        return userName;
    }

    public void setUserName(@NotEmpty(message = "UserName is required") String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
