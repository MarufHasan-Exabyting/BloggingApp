package com.example.BloggingApplication.controller;


import com.example.BloggingApplication.dto.ApiResponse;
import com.example.BloggingApplication.dto.CreateComment;
import com.example.BloggingApplication.dto.ResponseCommentDTO;
import com.example.BloggingApplication.model.Comment;
import com.example.BloggingApplication.service.CommentService;
import com.example.BloggingApplication.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<ResponseCommentDTO>> addComment(@Valid  @RequestBody CreateComment comment, HttpServletRequest request)
    {
        return ResponseEntity.ok(ResponseUtil.success(commentService.addComment(comment), "comment added successfully", request.getRequestURI()));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ResponseCommentDTO>>> getAllCommentsByPostId(@RequestParam(name = "postId")int postId, HttpServletRequest request)
    {
        return ResponseEntity.ok(ResponseUtil.success(commentService.getCommentsByPostId(postId), String.format("All comments of blogPost Id %d fetched successfully",postId), request.getRequestURI()));
    }
}