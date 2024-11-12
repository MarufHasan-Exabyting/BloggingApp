package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.model.User;

import java.util.List;

public interface UserDAO {
    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    User updateUser(User user);

    int deleteUser(int id);

    User getUserByEmail(String userEmail);

    User getUserByUserName(String userName);
}
