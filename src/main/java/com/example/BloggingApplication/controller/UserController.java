package com.example.BloggingApplication.controller;

import com.example.BloggingApplication.dto.ApiResponse;
import com.example.BloggingApplication.dto.CreateUserDTO;
import com.example.BloggingApplication.dto.ResponseUserDTO;
import com.example.BloggingApplication.dto.UpdateUserDTO;
import com.example.BloggingApplication.service.UserService;
import com.example.BloggingApplication.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<ResponseUserDTO> >  createUser(@Valid @RequestBody CreateUserDTO user, HttpServletRequest request)
    {
        ResponseUserDTO responseUserDTO = userService.createUser(user);
        return ResponseEntity.ok(ResponseUtil.success(responseUserDTO,"User created successfully", request.getRequestURI()));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ResponseUserDTO>> > getAllUsers(HttpServletRequest request)
    {
        List<ResponseUserDTO> responseUserDTOS = userService.getAllUsers();
        return ResponseEntity.ok(ResponseUtil.success(responseUserDTOS, "Successfully fetched all users", request.getRequestURI()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseUserDTO> > getUserById(@Valid @PathVariable int id, HttpServletRequest request)
    {
        ResponseUserDTO responseUserDTO = userService.getUserById(id);
        return ResponseEntity.ok(ResponseUtil.success(responseUserDTO, String.format("User with Id : %d successfully retrived",id),request.getRequestURI()));
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponse<ResponseUserDTO> > updateUser(@Valid @RequestBody UpdateUserDTO user, HttpServletRequest request)
    {
        ResponseUserDTO responseUserDTO = userService.updateUser(user);
        return ResponseEntity.ok(ResponseUtil.success(responseUserDTO, String.format("Succesflly updated the user with userId: %d",user.getUserId()),request.getRequestURI()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Integer>> deleteUser(@Valid @PathVariable int id, HttpServletRequest request)
    {
        int deletedCount = userService.deleteUser(id);
        return ResponseEntity.ok(ResponseUtil.success(deletedCount,String.format("The user with User Id : %d deleted successfully",id),request.getRequestURI()));
    }
}
