package com.example.BloggingApplication.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class UpdatePostDTO {
    @Positive
    @NotNull
    private int postId;

    @Positive(message = "AuthorId must be positive integer")
    private int postAuthorId;

    private String postTitle;

    private String content;

    private String category;

    public UpdatePostDTO() {
    }

    public UpdatePostDTO(int postId, String postTitle, int postAuthorId, String content, String category) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postAuthorId = postAuthorId;
        this.content = content;
        this.category = category;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public int getPostAuthorId() {
        return postAuthorId;
    }

    public void setPostAuthorId(int postAuthorId) {
        this.postAuthorId = postAuthorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
