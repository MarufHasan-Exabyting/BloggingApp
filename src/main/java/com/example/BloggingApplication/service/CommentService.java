package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dto.CreateComment;
import com.example.BloggingApplication.dto.ResponseCommentDTO;
import com.example.BloggingApplication.dto.UpdateCommentDTO;
import com.example.BloggingApplication.model.Comment;

import java.util.List;

public interface CommentService {
    public ResponseCommentDTO addComment(CreateComment comment);

    List<ResponseCommentDTO> getCommentsByPostId(int postId);

    Comment getCommentById(int commentId);

    ResponseCommentDTO updateComment(UpdateCommentDTO updateCommentDTO);

    int deleteCommentByCommentId(int commentId);
}
