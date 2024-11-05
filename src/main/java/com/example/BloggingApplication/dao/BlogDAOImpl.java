package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.model.BlogPost;
import com.example.BloggingApplication.model.EntityMetadata;
import com.example.BloggingApplication.model.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
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
        TypedQuery<BlogPost> query = getAllPostsQuery(title,authorName,category);
        return query.getResultList();
    }




    @Override
    public List<BlogPost> getPostByCategory(String postCategory) {
        String query = Common.getDynamicQuery(BlogPost.class,"category",postCategory);
        TypedQuery<BlogPost> typedQuery = entityManager.createQuery(query,BlogPost.class);
        return typedQuery.getResultList();
    }

    @Override
    public List<BlogPost> getPostByAuthor(String authorName) {
        UserProfile userProfile = getUserProfileByUserName(authorName);
        String query = Common.getDynamicQuery(BlogPost.class,"postAuthorId", userProfile);
        TypedQuery<BlogPost> blogPostTypedQuery = entityManager.createQuery(query,BlogPost.class);
        return blogPostTypedQuery.getResultList();
    }

    @Override
    public BlogPost updatePost(BlogPost blogPost) {
        BlogPost previousBlog = getBlogPostByPostId(blogPost.getPostId());
        EntityMetadata metadata = Common.getEntityMetadata(previousBlog.getMetadata().getCreatedAt(), new Date(System.currentTimeMillis()));
        blogPost.setMetadata(metadata);
        entityManager.merge(blogPost);
        return blogPost;
    }

    @Override
    public void deletePostById(int postId) {
        String query = Common.getDynamicQuery(BlogPost.class,"postId",postId);
        TypedQuery<BlogPost> typedQuery = entityManager.createQuery(query, BlogPost.class);
        BlogPost blogPost = typedQuery.getSingleResult();
        entityManager.remove(blogPost);
    }

    //****
    //Helper Methods
    //***
    private UserProfile getUserProfileByUserName(String userName)
    {
        String query = Common.getDynamicQuery(UserProfile.class,"userName",userName);
        TypedQuery<UserProfile> userProfileTypedQuery = entityManager.createQuery(query, UserProfile.class);

        return userProfileTypedQuery.getSingleResult();
    }

    private BlogPost getBlogPostByPostId(int postId)
    {
        String query = Common.getDynamicQuery(BlogPost.class,"postId",postId);
        TypedQuery<BlogPost> blogPostTypedQuery = entityManager.createQuery(query, BlogPost.class);
        return blogPostTypedQuery.getSingleResult();
    }

    private TypedQuery<BlogPost> getAllPostsQuery(String title, String authorName, String category)
    {
        //should be in separate utility function
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
        return query;
    }
}
