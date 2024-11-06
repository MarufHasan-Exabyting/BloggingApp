package com.example.BloggingApplication.controller;

import com.example.BloggingApplication.dto.CreateUserDTO;
import com.example.BloggingApplication.dto.ResponseUserDTO;
import com.example.BloggingApplication.dto.UpdateUserDTO;
import com.example.BloggingApplication.service.UserService;
import jakarta.validation.Valid;
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

    //ok
    @PostMapping("/")
    public ResponseUserDTO createUser(@Valid @RequestBody CreateUserDTO user)
    {
        return userService.createUser(user);
    }
    //ok
    @GetMapping("/")
    public List<ResponseUserDTO> getAllUsers()
    {
        return userService.getAllUsers();
    }
    //ok
    @GetMapping("/{id}")
    public ResponseUserDTO getUserById(@Valid @PathVariable int id)
    {
        //System.out.println(bindingResult.getAllErrors());
        return userService.getUserById(id);
    }
    //ok
    @PutMapping("/")
    public ResponseUserDTO updateUser(@Valid @RequestBody UpdateUserDTO user)
    {
        return userService.updateUser(user);
    }

    //ok
    @DeleteMapping("/{id}")
    public void deleteUser(@Valid @PathVariable int id)
    {
        userService.deleteUser(id);
    }
}
