package com.example.UserDemo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_Profile")
public class UserProfile {
    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userProfileId;
    
    @Column(name = "name")
    private String userName;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIME)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIME)
    private Date updatedAt;

    @Column(name = "email")
    private String userEmail;

    @OneToOne
    @JoinColumn(name = "fk_created_by")
    private User createdBy;

    @OneToMany(mappedBy = "postAuthorId", fetch = FetchType.LAZY)
    List<BlogPost> blogPosts;

    public UserProfile() {
    }

    public UserProfile(int userProfileId, String userName, Date createdAt, Date updatedAt, String userEmail, User createdBy, List<BlogPost> blogPosts) {
        this.userProfileId = userProfileId;
        this.userName = userName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userEmail = userEmail;
        this.createdBy = createdBy;
        this.blogPosts = blogPosts;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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
}
