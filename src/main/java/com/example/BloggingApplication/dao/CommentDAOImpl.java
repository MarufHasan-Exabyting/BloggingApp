package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.exception.CommentNotFoundException;
import com.example.BloggingApplication.model.BlogPost;
import com.example.BloggingApplication.model.Comment;
import com.example.BloggingApplication.model.EntityMetadata;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDAOImpl implements CommentDAO{

    private EntityManager entityManager;
    private BlogDAO blogDAO;

    public CommentDAOImpl() {
    }

    @Autowired
    public CommentDAOImpl(EntityManager entityManager, BlogDAO blogDAO) {
        this.entityManager = entityManager;
        this.blogDAO = blogDAO;
    }

    @Override
    public Comment addComment(Comment comment) {
        entityManager.persist(comment);
        return comment;
    }

    @Override
    public List<Comment> getCommentsByPostId(int postId) {
        BlogPost blogPost = blogDAO.getPostById(postId);
        TypedQuery<Comment> typedQuery = entityManager.createQuery("From Comment where blogPost = :post",Comment.class);
        typedQuery.setParameter("post",blogPost);
        List<Comment> comments = typedQuery.getResultList();
        if(comments.isEmpty())
        {
            throw new CommentNotFoundException(String.format("Comments for the BlogPost with Id: %d not found",postId));
        }
        return comments;
    }
}
