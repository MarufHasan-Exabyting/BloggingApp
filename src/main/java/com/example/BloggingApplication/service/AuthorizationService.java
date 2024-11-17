package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dao.BlogDAO;
import com.example.BloggingApplication.dao.CommentDAO;
import com.example.BloggingApplication.dao.UserDAO;
import com.example.BloggingApplication.dao.UserProfileDAO;
import com.example.BloggingApplication.model.*;
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
        String userName = jwtService.extractUserName(token);
        User user = userDAO.getUserById(deleteId);
        
        return userName.equals(user.getUserName()) || jwtService.extractRole(token) == Role.ROLE_ADMIN;
    }

    public boolean checkIfAuthorizeToUpdate(int userId, HttpServletRequest request) {
        User user = userDAO.getUserById(userId);
        String token = Utility.getTokenFromRequest(request);
        String requestedUserName = jwtService.extractUserName(token);

        //checking whether the tokenOwner and updatingProfileOwner same or not
        return requestedUserName.equals(user.getUserName());
    }

    public boolean checkIfAuthorizeToCreateBlog(int postAuthorId, HttpServletRequest request)
    {
        User postAuthor = userDAO.getUserById(postAuthorId);
        String token = Utility.getTokenFromRequest(request);
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

        String blogUpdaterName = jwtService.extractUserName(token);

        return blogUpdaterName.equals(blogPostAuthorById.getUserName()) && (blogUpdaterName.equals(PostAuthor.getUserName()));
    }

    public boolean checkIfAuthorizeToDeleteBlog(@Valid int postId, HttpServletRequest request) {
        BlogPost blogPost = blogDAO.getPostById(postId);
        UserProfile userProfile = blogPost.getPostAuthorId();
        String blogPostAuthorUserName = userProfile.getUserName();
        String token = Utility.getTokenFromRequest(request);

        String requesterName = jwtService.extractUserName(token);
        Role role = jwtService.extractRole(token);

        return blogPostAuthorUserName.equals(requesterName) || role == Role.ROLE_ADMIN;
    }

    public boolean checkIfAuthorizeToComment(int commentatorId, HttpServletRequest request) {
        String token = Utility.getTokenFromRequest(request);

        String commentatorFromRequest = jwtService.extractUserName(token);
        UserProfile userProfile = userProfileDAO.getUserProfileByUserProfileId(commentatorId);
        String userName = userProfile.getUserName();
        return userName.equals(commentatorFromRequest);
    }

    public boolean checkIfAuthorizeToUpdateComment(int commentId, HttpServletRequest request) {
        Comment comment = commentDAO.getCommentByCommentId(commentId);
        UserProfile userProfile = comment.getCommentator();
        String userName = userProfile.getUserName();
        String token = Utility.getTokenFromRequest(request);
        String commentUpdatedBy = jwtService.extractUserName(token);
        return commentUpdatedBy.equals(userName);
    }

    public boolean checkIfAuthorizeToDeleteComment(@Valid int commentId, HttpServletRequest request) {
        Comment comment = commentDAO.getCommentByCommentId(commentId);
        UserProfile commentator = comment.getCommentator();
        BlogPost blogPost = comment.getBlogPost();
        UserProfile blogPostUserProfile = blogPost.getPostAuthorId();
        String blogPostAuthor = blogPostUserProfile.getUserName();
        String token = Utility.getTokenFromRequest(request);

        String requesterUserName = jwtService.extractUserName(token);
        return requesterUserName.equals(commentator.getUserName()) || requesterUserName.equals(blogPostAuthor) || requesterUserName.equals("admin");
    }
}
