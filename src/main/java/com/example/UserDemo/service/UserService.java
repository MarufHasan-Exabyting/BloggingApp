package com.example.UserDemo.service;

import com.example.UserDemo.dto.CreateUserDTO;
import com.example.UserDemo.dto.ResponseUserDTO;
import com.example.UserDemo.dto.UpdateUserDTO;


import java.util.List;

public interface UserService {
    public ResponseUserDTO createUser(CreateUserDTO user);

    public List<ResponseUserDTO> getAllUsers();

    public ResponseUserDTO getUserById(int id);

    public ResponseUserDTO updateUser(UpdateUserDTO user);

    public void deleteUser(int id);
}
