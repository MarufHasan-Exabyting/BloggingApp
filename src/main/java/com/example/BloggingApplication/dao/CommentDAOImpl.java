package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.exception.CommentNotFoundException;
import com.example.BloggingApplication.model.BlogPost;
import com.example.BloggingApplication.model.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.Date;
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
        TypedQuery<Comment> typedQuery = entityManager.createQuery("From Comment where blogPost = :post and metadata.isDeleted = false",Comment.class);
        typedQuery.setParameter("post",blogPost);
        List<Comment> comments = typedQuery.getResultList();
        if(comments.isEmpty())
        {
            throw new CommentNotFoundException(String.format("Comments for the BlogPost with Id: %d not found",postId));
        }
        return comments;
    }

    @Override
    public Comment getCommentByCommentId(int commentId) {
        TypedQuery<Comment> typedQuery = entityManager.createQuery("From Comment where commentId = :comment_id and metadata.isDeleted = false",Comment.class);
        typedQuery.setParameter("comment_id",commentId);
        List<Comment> comments = typedQuery.getResultList();
        if(comments.isEmpty())
        {
            throw new CommentNotFoundException(String.format("Comment with Comment Id %d not found",commentId));
        }
        return comments.getFirst();
    }

    @Override
    public Comment updateComment(Comment updatedComment) {
        return entityManager.merge(updatedComment);
    }

    @Override
    public int deleteCommentByCommentId(int commentId) {
        Comment comment = getCommentByCommentId(commentId);
        comment.getMetadata().setDeletedAt(new Date(System.currentTimeMillis()));
        comment.getMetadata().setDeleted(true);
        return 1;
    }
}
