package com.example.UserDemo.dao;

import com.example.UserDemo.dto.CreateUserDTO;
import com.example.UserDemo.dto.ResponseUserDTO;
import com.example.UserDemo.model.User;

import java.util.List;

public interface UserDAO {
    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    User updateUser(User user);

    void deleteUser(int id);
}
