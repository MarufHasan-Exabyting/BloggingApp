package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dto.CreateUserDTO;
import com.example.BloggingApplication.dto.ResponseUserDTO;
import com.example.BloggingApplication.dto.UpdateUserDTO;


import java.util.List;

public interface UserService {
    public ResponseUserDTO createUser(CreateUserDTO user);

    public List<ResponseUserDTO> getAllUsers();

    public ResponseUserDTO getUserById(int id);

    public ResponseUserDTO updateUser(UpdateUserDTO user);

    public void deleteUser(int id);
}
