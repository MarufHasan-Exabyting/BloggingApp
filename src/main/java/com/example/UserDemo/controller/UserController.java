package com.example.UserDemo.controller;

import com.example.UserDemo.dto.CreateUserDTO;
import com.example.UserDemo.dto.ResponseUserDTO;
import com.example.UserDemo.dto.UpdateUserDTO;
import com.example.UserDemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseUserDTO createUser(@Valid @RequestBody CreateUserDTO user)
    {
        return userService.createUser(user);
    }

    @GetMapping("/")
    public List<ResponseUserDTO> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseUserDTO getUserById(@Valid @PathVariable int id)
    {
        //System.out.println(bindingResult.getAllErrors());
        return userService.getUserById(id);
    }

    @PutMapping("/")
    public ResponseUserDTO updateUser(@Valid @RequestBody UpdateUserDTO user)
    {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@Valid @PathVariable int id)
    {
        userService.deleteUser(id);
    }
}
