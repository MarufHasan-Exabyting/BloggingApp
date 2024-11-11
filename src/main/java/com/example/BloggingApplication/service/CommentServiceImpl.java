package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dao.*;
import com.example.BloggingApplication.dto.CreateComment;
import com.example.BloggingApplication.dto.ResponseCommentDTO;
import com.example.BloggingApplication.model.BlogPost;
import com.example.BloggingApplication.model.Comment;
import com.example.BloggingApplication.model.EntityMetadata;
import com.example.BloggingApplication.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    private CommentDAO commentDAO;
    private BlogDAO blogDAO;
    private UserProfileDAO userProfileDAO;

    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO, BlogDAO blogDAO, UserProfileDAO userProfileDAO) {
        this.commentDAO = commentDAO;
        this.blogDAO = blogDAO;
        this.userProfileDAO = userProfileDAO;
    }

    @Override
    @Transactional
    public ResponseCommentDTO addComment(CreateComment createComment) {
        Comment addedComment = commentDAO.addComment(toComment(createComment));
        return toResponseCommentDTO(addedComment);
    }

    @Override
    public List<ResponseCommentDTO> getCommentsByPostId(int postId) {
        return responseCommentDTOList(commentDAO.getCommentsByPostId(postId));
    }


    //Helper Functions
    private Comment toComment(CreateComment createComment)
    {
        EntityMetadata metadata = new EntityMetadata();
        metadata.setDeleted(false);
        metadata.setDeletedAt(null);
        metadata.setCreatedAt(new Date(System.currentTimeMillis()));
        metadata.setUpdatedAt(new Date(System.currentTimeMillis()));

        BlogPost blogPost = blogDAO.getPostById(createComment.getBlogPostId());
        UserProfile userProfile = userProfileDAO.getUserProfileByUserId(createComment.getCommentatorId());

        Comment comment = new Comment();
        comment.setContent(createComment.getComment());
        comment.setMetadata(metadata);
        comment.setCommentator(userProfile);
        comment.setBlogPost(blogPost);
        blogPost.getComments().add(comment);
        userProfile.getComments().add(comment);
        return comment;
    }

    private List<ResponseCommentDTO> responseCommentDTOList(List<Comment> comments)
    {
        List<ResponseCommentDTO> responseCommentDTOList = new ArrayList<>();
        for(Comment comment : comments)
        {
            responseCommentDTOList.add(toResponseCommentDTO(comment));
        }
        return responseCommentDTOList;
    }

    private ResponseCommentDTO toResponseCommentDTO(Comment addedComment) {
        ResponseCommentDTO responseCommentDTO = new ResponseCommentDTO();
        responseCommentDTO.setCommentId(addedComment.getCommentId());
        responseCommentDTO.setComment(addedComment.getContent());
        responseCommentDTO.setBlogPostId(addedComment.getBlogPost().getPostId());
        responseCommentDTO.setCommentator(addedComment.getCommentator().getUserName());
        responseCommentDTO.setCommentedAt(addedComment.getMetadata().getCreatedAt());
        return responseCommentDTO;
    }
}
