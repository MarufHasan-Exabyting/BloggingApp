package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dao.BlogDAO;
import com.example.BloggingApplication.dao.CommentDAO;
import com.example.BloggingApplication.dao.UserDAO;
import com.example.BloggingApplication.dao.UserProfileDAO;
import com.example.BloggingApplication.model.BlogPost;
import com.example.BloggingApplication.model.Role;
import com.example.BloggingApplication.model.User;
import com.example.BloggingApplication.model.UserProfile;
import com.example.BloggingApplication.util.Utility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorizationService {
    private UserDAO userDAO;
    private BlogDAO blogDAO;
    private UserProfileDAO userProfileDAO;
    private CommentDAO commentDAO;
    private JWTService jwtService;

    @Autowired
    public AuthorizationService(UserDAO userDAO, BlogDAO blogDAO, CommentDAO commentDAO,
                                JWTService jwtService, UserProfileDAO userProfileDAO) {
        this.userDAO = userDAO;
        this.blogDAO = blogDAO;
        this.commentDAO = commentDAO;
        this.jwtService = jwtService;
        this.userProfileDAO = userProfileDAO;
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

    public boolean checkIfAuthorizeToUpdate(int userId, HttpServletRequest request) {
        User user = userDAO.getUserById(userId);
        String token = Utility.getTokenFromRequest(request);
        if(token == null)
        {
            return false;
        }
        String requestedUserName = jwtService.extractUserName(token);
        //checking whether the tokenOwner and updatingProfileOwner same or not
        if(requestedUserName.equals(user.getUserName()))
        {
            return true;
        }
        return false;
    }

    public boolean checkIfAuthorizeToCreateBlog(int postAuthorId, HttpServletRequest request)
    {
        User postAuthor = userDAO.getUserById(postAuthorId);
        String token = Utility.getTokenFromRequest(request);
        if(token == null)
        {
            return false;
        }
        String blogCreatorUserName = jwtService.extractUserName(token);
        return blogCreatorUserName.equals(postAuthor.getUserName());
    }

    public boolean checkIfAuthorizeToUpdateBlog(int postId, int postAuthorId, HttpServletRequest request) {
        //Author of the post from userName (get userName using PostId)
        // AuthorId matches with same UserProfile
        BlogPost blogPost = blogDAO.getPostById(postId);

        //getUserProfile from UserProfileId of blogpost object
        UserProfile PostAuthor = blogPost.getPostAuthorId();


        UserProfile blogPostAuthorById = userProfileDAO.getUserProfileByUserId(postAuthorId);
        String token = Utility.getTokenFromRequest(request);
        if(token == null)
        {
            return false;
        }
        String blogUpdaterName = jwtService.extractUserName(token);
        if(blogUpdaterName.equals(blogPostAuthorById.getUserName()) && (blogUpdaterName.equals(PostAuthor.getUserName())))
        {
            return true;
        }
        return false;
    }

    public boolean checkIfAuthorizeToDeleteBlog(@Valid int postId, HttpServletRequest request) {
        BlogPost blogPost = blogDAO.getPostById(postId);
        UserProfile userProfile = blogPost.getPostAuthorId();
        String blogPostAuthorUserName = userProfile.getUserName();
        String token = Utility.getTokenFromRequest(request);
        if(token == null)
        {
            return false;
        }
        String requesterName = jwtService.extractUserName(token);
        Role role = jwtService.extractRole(token);
        if(blogPostAuthorUserName.equals(requesterName) || role == Role.ROLE_ADMIN)
        {
            return true;
        }
        return false;
    }
}
