package com.example.UserDemo.controller;

import com.example.UserDemo.dto.CreateUserDTO;
import com.example.UserDemo.dto.ResponseUserDTO;
import com.example.UserDemo.dto.UpdateUserDTO;
import com.example.UserDemo.model.User;
import com.example.UserDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseUserDTO createUser(@RequestBody CreateUserDTO user)
    {
        System.out.println("createUserDTO: " + user);
        var response = userService.createUser(user);
        System.out.println(response);
        return response;
    }

    @GetMapping("/")
    public List<ResponseUserDTO> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseUserDTO getUserById(@PathVariable int id)
    {
        return userService.getUserById(id);
    }

    @PutMapping("/")
    public ResponseUserDTO updateUser(@RequestBody UpdateUserDTO user)
    {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id)
    {
        userService.deleteUser(id);
    }
}
