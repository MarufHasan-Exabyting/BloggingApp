package com.example.BloggingApplication.dto;


import java.util.Date;

public class ResponseBlogPostDTO {
    private int postId;
    private String postTitle;
    private String postAuthorName;
    private String content;
    private String category;
    private Date createdAt;

    public ResponseBlogPostDTO() {
    }

    public ResponseBlogPostDTO(int postId, String postTitle, String postAuthorName, String content, String category, Date createdAt) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postAuthorName = postAuthorName;
        this.content = content;
        this.category = category;
        this.createdAt = createdAt;
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

    public String getPostAuthorName() {
        return postAuthorName;
    }

    public void setPostAuthorName(String postAuthorName) {
        this.postAuthorName = postAuthorName;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
