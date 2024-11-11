package com.example.BloggingApplication.dto;

public class UpdateCommentDTO {
    private int commentId;
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
