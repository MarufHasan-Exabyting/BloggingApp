package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dao.BlogDAO;
import com.example.BloggingApplication.dao.CommentDAO;
import com.example.BloggingApplication.dao.UserDAO;
import com.example.BloggingApplication.model.Role;
import com.example.BloggingApplication.model.User;
import com.example.BloggingApplication.util.Utility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class AuthorizationService {
    private UserDAO userDAO;
    private BlogDAO blogDAO;
    private CommentDAO commentDAO;
    private JWTService jwtService;

    @Autowired
    public AuthorizationService(UserDAO userDAO, BlogDAO blogDAO, CommentDAO commentDAO, JWTService jwtService) {
        this.userDAO = userDAO;
        this.blogDAO = blogDAO;
        this.commentDAO = commentDAO;
        this.jwtService = jwtService;
    }

    public boolean checkIfAuthorizeToDeleteUser(int deleteId, HttpServletRequest request)
    {
        String token = Utility.getTokenFromRequest(request);
        if(token == null)
        {
            return false;
        }
        String userName = jwtService.extractUserName(token);
        User user = userDAO.getUserById(deleteId);
        
        return userName.equals(user.getUserName()) || jwtService.extractRole(token) == Role.ROLE_ADMIN;
    }
}
