package com.example.BloggingApplication.model;


import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user_Profile")
public class UserProfile {
    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userProfileId;
    
    @Column(name = "user_name")
    private String userName;

    @Embedded
    private EntityMetadata metadata;

    @Column(name = "email")
    private String userEmail;

    @OneToOne
    @JoinColumn(name = "fk_created_by")
    private User createdBy;

    @OneToMany(mappedBy = "postAuthorId", fetch = FetchType.LAZY)
    List<BlogPost> blogPosts;

    @OneToMany(mappedBy = "commentator")
    List<Comment> comments = new LinkedList<>();

    public UserProfile() {
    }

    public UserProfile(int userProfileId, String userName, EntityMetadata metadata, String userEmail, User createdBy, List<BlogPost> blogPosts, List<Comment> comments) {
        this.userProfileId = userProfileId;
        this.userName = userName;
        this.metadata = metadata;
        this.userEmail = userEmail;
        this.createdBy = createdBy;
        this.blogPosts = blogPosts;
        this.comments = comments;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public int getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
    }

    public List<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
