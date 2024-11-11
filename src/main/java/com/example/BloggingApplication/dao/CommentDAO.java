package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.model.Comment;

import java.util.List;

public interface CommentDAO {
    Comment addComment(Comment comment);
    List<Comment> getCommentsByPostId(int postId);

    Comment getCommentByCommentId(int commentId);

    Comment updateComment(Comment updatedComment);

    int deleteCommentByCommentId(int commentId);
}
