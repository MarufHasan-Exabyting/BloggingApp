package com.example.BloggingApplication.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "content",length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "fk_post_id")
    @JsonIgnore
    private BlogPost blogPost;

    @ManyToOne
    @JoinColumn(name = "fk_commentator_id")
    @JsonIgnore
    private UserProfile commentator;

    @Embedded
    private EntityMetadata metadata;

    public Comment() {
    }

    public Comment(int commentId, String content, BlogPost blogPost, UserProfile commentator, EntityMetadata metadata) {
        this.commentId = commentId;
        this.content = content;
        this.blogPost = blogPost;
        this.commentator = commentator;
        this.metadata = metadata;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public UserProfile getCommentator() {
        return commentator;
    }

    public void setCommentator(UserProfile commentator) {
        this.commentator = commentator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EntityMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(EntityMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                ", blogPost=" + blogPost +
                ", commentator=" + commentator +
                ", metadata=" + metadata +
                '}';
    }
}
