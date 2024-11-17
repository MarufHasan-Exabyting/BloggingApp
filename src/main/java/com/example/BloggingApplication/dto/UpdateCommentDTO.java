package com.example.BloggingApplication.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class UpdateCommentDTO {

    @Positive(message = "blogPostId can not be negative")
    private int commentId;

    @NotEmpty
    @Size(min = 1,max = 500,message = "comment should not be empty")
    private String updatedComment;

    public UpdateCommentDTO() {
    }

    public UpdateCommentDTO(int commentId, String updatedComment) {
        this.commentId = commentId;
        this.updatedComment = updatedComment;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getUpdatedComment() {
        return updatedComment;
    }

    public void setUpdatedComment(String updatedComment) {
        this.updatedComment = updatedComment;
    }
}
