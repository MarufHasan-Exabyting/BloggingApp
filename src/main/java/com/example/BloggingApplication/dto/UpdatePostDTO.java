package com.example.BloggingApplication.dto;


public class UpdatePostDTO {
    private int postId;
    private String postTitle;
    private int postAuthorId;
    private String content;
    private String category;

    public UpdatePostDTO(int postId) {
        this.postId = postId;
    }

    public UpdatePostDTO(int postId, String postTitle,int postAuthorId, String content, String category) {
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
