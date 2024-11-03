package com.example.UserDemo.dto;

public class CreatePostDTO {
    private String postTitle;
    private String content;
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
