package com.example.BloggingApplication.controller;

import com.example.BloggingApplication.dto.*;
import com.example.BloggingApplication.exception.AuthorizationException;
import com.example.BloggingApplication.service.AuthorizationService;
import com.example.BloggingApplication.service.UserService;
import com.example.BloggingApplication.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/")
public class UserController {
    private UserService userService;
    private AuthorizationService authorizationService;

    @Autowired
    public UserController(UserService userService, AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<ApiResponse<ResponseUserDTO> >  createUser(@Valid @RequestBody CreateUserDTO user, HttpServletRequest request)
    {
        ResponseUserDTO responseUserDTO = userService.RegisterUser(user);
        return ResponseEntity.ok(ResponseUtil.success(responseUserDTO,"User created successfully", request.getRequestURI()));
    }

    @PostMapping("/users/login")
    public ResponseEntity<ApiResponse<Map<String,String>>> logInUser(@Valid @RequestBody LogInDTO logInDTO, HttpServletRequest request)
    {
        return ResponseEntity.ok(ResponseUtil.success(userService.verifyLogin(logInDTO),"UserName and Password Verification",request.getRequestURI()));
    }

    @GetMapping("/admin/users")
    public ResponseEntity<ApiResponse<List<ResponseUserDTO>> > getAllUsers(HttpServletRequest request)
    {
        List<ResponseUserDTO> responseUserDTOS = userService.getAllUsers();
        return ResponseEntity.ok(ResponseUtil.success(responseUserDTOS, "Successfully fetched all users", request.getRequestURI()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<ResponseUserDTO> > getUserById(@Valid @PathVariable int id, HttpServletRequest request)
    {
        ResponseUserDTO responseUserDTO = userService.getUserById(id);
        return ResponseEntity.ok(ResponseUtil.success(responseUserDTO, String.format("User with Id : %d successfully retrieved",id),request.getRequestURI()));
    }

    //needs to change the UpdateDTO and respective logic
    @PutMapping("/users")
    public ResponseEntity<ApiResponse<ResponseUserDTO> > updateUser(@Valid @RequestBody UpdateUserDTO user, HttpServletRequest request)
    {
        boolean canUpdate = authorizationService.checkIfAuthorizeToUpdate(user.getUserId(),request);
        if(!canUpdate)
        {
            throw new AuthorizationException("Does not have permission to Update the User with UserId : " + user.getUserId());
        }
        ResponseUserDTO responseUserDTO = userService.updateUser(user);
        return ResponseEntity.ok(ResponseUtil.success(responseUserDTO, String.format("Successfully updated the user with userId: %d",user.getUserId()),request.getRequestURI()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Integer>> deleteUser(@Valid @PathVariable int id, HttpServletRequest request)
    {
        boolean canDelete = authorizationService.checkIfAuthorizeToDeleteUser(id,request);
        if(!canDelete)
        {
            throw new AuthorizationException("Does not have permission to delete the Id : " + id);
        }

        int deletedCount = userService.deleteUser(id);
        return ResponseEntity.ok(ResponseUtil.success(deletedCount,String.format("The user with User Id : %d deleted successfully",id),request.getRequestURI()));
    }
}
