package com.example.BloggingApplication.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
@Table(name = "blogpost")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    @Column(name = "post_title")
    private String postTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_post_author_id")
    @JsonIgnore
    private UserProfile postAuthorId;

    @Column(name = "content")
    private String content;

    @Column(name = "category")
    private String category;

    @Embedded
    private EntityMetadata metadata;

    public BlogPost() {
    }

    public BlogPost(int postId, String postTitle, UserProfile postAuthorId, String content, String category) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postAuthorId = postAuthorId;
        this.content = content;
        this.category = category;
    }

    public UserProfile getPostAuthorId() {
        return postAuthorId;
    }

    public void setPostAuthorId(UserProfile postAuthorId) {
        this.postAuthorId = postAuthorId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public EntityMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(EntityMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", postAuthorId=" + postAuthorId +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}