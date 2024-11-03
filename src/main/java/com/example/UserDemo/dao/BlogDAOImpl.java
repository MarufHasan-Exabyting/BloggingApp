package com.example.UserDemo.dao;

import com.example.UserDemo.model.BlogPost;
import com.example.UserDemo.model.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlogDAOImpl implements BlogDAO{
    private EntityManager entityManager;

    @Autowired
    public BlogDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public BlogPost createPost(BlogPost blogPost) {
        entityManager.persist(blogPost);
        return blogPost;
    }

    @Override
    public List<BlogPost> getAllPosts() {
        TypedQuery<BlogPost> query = entityManager.createQuery("From BlogPost",BlogPost.class);
        return query.getResultList();
    }

    @Override
    public List<BlogPost> getPostByTitle(String Title) {
        TypedQuery<BlogPost> query = entityManager.createQuery("From BlogPost where postTitle:=title",BlogPost.class);
        query.setParameter("title",Title);
        return query.getResultList();
    }

    @Override
    public List<BlogPost> getPostByCategory(String Category) {
        return List.of();
    }

    @Override
    public List<BlogPost> getPostByAuthor(String authorName) {
        TypedQuery<UserProfile> query = entityManager.createQuery("From UserProfile where userName = :userProfileName",UserProfile.class);
        query.setParameter("userProfileName",authorName);
        List<UserProfile> userProfiles = query.getResultList();
        UserProfile userProfile = userProfiles.getFirst();
        TypedQuery<BlogPost> blogPostTypedQuery = entityManager.createQuery("From BlogPost where postAuthorId = :userProfile",BlogPost.class);
        blogPostTypedQuery.setParameter("userProfile",userProfile);
        return blogPostTypedQuery.getResultList();
    }

    @Override
    public BlogPost updatePost(BlogPost blogPost) {
        return null;
    }

    @Override
    public void deletePostById(int postId) {

    }
}
