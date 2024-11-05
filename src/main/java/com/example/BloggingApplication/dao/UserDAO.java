package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.model.User;

import java.util.List;

public interface UserDAO {
    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    User updateUser(User user);

    void deleteUser(int id);
}
