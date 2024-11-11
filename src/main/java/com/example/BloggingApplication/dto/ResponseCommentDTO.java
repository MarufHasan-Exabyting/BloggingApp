package com.example.BloggingApplication.dto;

import com.example.BloggingApplication.model.BlogPost;
import com.example.BloggingApplication.model.EntityMetadata;
import com.example.BloggingApplication.model.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

public class ResponseCommentDTO {
    private int commentId;
    private String comment;
    private int blogPostId;
    private String commentator;
    private Date commentedAt;

    public ResponseCommentDTO() {
    }

    public ResponseCommentDTO(int commentId, String comment, int blogPostTitle, String commentator, Date commentedAt) {
        this.commentId = commentId;
        this.comment = comment;
        this.blogPostId = blogPostTitle;
        this.commentator = commentator;
        this.commentedAt = commentedAt;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(int blogPostId) {
        this.blogPostId = blogPostId;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }

    public Date getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(Date commentedAt) {
        this.commentedAt = commentedAt;
    }
}
