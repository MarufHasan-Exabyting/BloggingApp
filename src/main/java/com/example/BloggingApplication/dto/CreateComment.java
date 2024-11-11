package com.example.BloggingApplication.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateComment {
    @NotEmpty
    @Size(min = 1,max = 500,message = "comment should not be empty")
    private String comment;

    @Positive(message = "blogPostId can not be negative")
    private int blogPostId;

    @Positive(message = "commentatorId can not be negative")
    private int commentatorId;

    public CreateComment() {
    }

    public CreateComment(String comment, int blogPostId, int commentatorId) {
        this.comment = comment;
        this.blogPostId = blogPostId;
        this.commentatorId = commentatorId;
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

    public int getCommentatorId() {
        return commentatorId;
    }

    public void setCommentatorId(int commentatorId) {
        this.commentatorId = commentatorId;
    }

    @Override
    public String toString() {
        return "CreateComment{" +
                "comment='" + comment + '\'' +
                ", blogPostId=" + blogPostId +
                ", commentatorId=" + commentatorId +
                '}';
    }
}
