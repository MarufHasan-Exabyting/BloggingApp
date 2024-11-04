package com.example.UserDemo.dao;

import com.example.UserDemo.model.BlogPost;
import com.example.UserDemo.model.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public List<BlogPost> getAllPosts(String title, String authorName, String category) {
        //1=1 is added for the condition to be true always
        //so that AND condition can be appended
        StringBuilder jpql = new StringBuilder("From BlogPost where 1=1");

        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        int parameterIndex = 1;

        if (title != null && !title.isEmpty()) {
            conditions.add(String.format("postTitle = ?%d",parameterIndex++));
            parameters.add(title);
        }
        if (authorName != null && !authorName.isEmpty())
        {
            conditions.add(String.format("authorName = ?%d",parameterIndex++));
            parameters.add(authorName);
        }
        if(category != null && !category.isEmpty())
        {
            conditions.add(String.format("category = ?%d",parameterIndex++));
            parameters.add(category);
        }

        for(int i=0; i < conditions.size(); i++)
        {
            jpql.append(" AND ");
            jpql.append(conditions.get(i));
        }

        TypedQuery<BlogPost> query = entityManager.createQuery(jpql.toString(),BlogPost.class);

        for(int i=0; i < parameters.size(); i++)
        {
            query.setParameter(i+1,parameters.get(i));
        }

        return query.getResultList();
    }

    @Override
    public List<BlogPost> getPostByCategory(String postCategory) {
        TypedQuery<BlogPost> query = entityManager.createQuery("From BlogPost where category = :blog_category",BlogPost.class);
        query.setParameter("blog_category",postCategory);
        return query.getResultList();
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
        entityManager.persist(blogPost);
        return blogPost;
    }

    @Override
    public void deletePostById(int postId) {
        TypedQuery<BlogPost> query = entityManager.createQuery("From BlogPost where postId = :post_id",BlogPost.class);
        query.setParameter("post_id",postId);
        BlogPost blogPost = query.getResultList().getFirst();
        entityManager.remove(blogPost);
    }
}
