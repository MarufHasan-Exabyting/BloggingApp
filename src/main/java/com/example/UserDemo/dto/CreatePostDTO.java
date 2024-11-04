package com.example.UserDemo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreatePostDTO {
    @NotEmpty(message = "Title must not be empty")
    @Size(min = 3, max = 100,message = "The length of the title should be between 3 to 100")
    private String postTitle;

    @NotEmpty(message = "BlogContent should not be empty")
    @Size(min = 5, max = 5000,message = "The length of the title should be between 3 to 1000")
    private String content;

    @Positive(message = "AuthorId must be positive integer")
    private int postAuthorId;

    private String category;

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPostAuthorId() {
        return postAuthorId;
    }

    public void setPostAuthorId(int postAuthorId) {
        this.postAuthorId = postAuthorId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CreatePostDTO{" +
                "postTitile='" + postTitle + '\'' +
                ", content='" + content + '\'' +
                ", postAuthorId=" + postAuthorId +
                ", category='" + category + '\'' +
                '}';
    }
}
