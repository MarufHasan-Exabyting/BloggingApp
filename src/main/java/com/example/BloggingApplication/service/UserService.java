package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dto.CreateUserDTO;
import com.example.BloggingApplication.dto.LogInDTO;
import com.example.BloggingApplication.dto.ResponseUserDTO;
import com.example.BloggingApplication.dto.UpdateUserDTO;
import jakarta.validation.Valid;


import java.util.List;

public interface UserService {
    public ResponseUserDTO RegisterUser(CreateUserDTO user);

    public List<ResponseUserDTO> getAllUsers();

    public ResponseUserDTO getUserById(int id);

    public ResponseUserDTO updateUser(UpdateUserDTO user);

    public int deleteUser(int id);

    String verifyLogin(@Valid LogInDTO logInDTO);
}
