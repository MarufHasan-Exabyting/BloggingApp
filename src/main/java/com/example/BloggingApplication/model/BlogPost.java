package com.example.BloggingApplication.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name = "blogpost")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    @Column(name = "post_title")
    private String postTitle;

    @ManyToOne()
    @JoinColumn(name = "fk_post_author_id")
    @JsonIgnore
    private UserProfile postAuthorId;

    @Column(name = "content", length = 5000)
    private String content;

    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "blogPost")
    @JsonIgnore
    List<Comment> comments = new LinkedList<>();

    @Embedded
    private EntityMetadata metadata;

    public BlogPost() {
    }

    public BlogPost(int postId, String postTitle, UserProfile postAuthorId, String content,
                    String category, LinkedList<Comment> comments, EntityMetadata metadata)
    {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postAuthorId = postAuthorId;
        this.content = content;
        this.category = category;
        this.comments = comments;
        this.metadata = metadata;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(LinkedList<Comment> comments) {
        this.comments = comments;
    }

    public void addComments(Comment comment)
    {
        this.comments.add(comment);
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
