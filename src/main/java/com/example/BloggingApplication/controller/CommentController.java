package com.example.BloggingApplication.controller;


import com.example.BloggingApplication.dto.ApiResponse;
import com.example.BloggingApplication.dto.CreateComment;
import com.example.BloggingApplication.dto.ResponseCommentDTO;
import com.example.BloggingApplication.dto.UpdateCommentDTO;
import com.example.BloggingApplication.exception.AuthorizationException;
import com.example.BloggingApplication.service.AuthorizationService;
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
    private AuthorizationService authorizationService;
    @Autowired
    public CommentController(CommentService commentService, AuthorizationService authorizationService) {
        this.commentService = commentService;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<ResponseCommentDTO>> addComment(@Valid  @RequestBody CreateComment comment, HttpServletRequest request)
    {
        if(!authorizationService.checkIfAuthorizeToComment(comment.getCommentatorId(),request))
        {
            throw new AuthorizationException("CommentatorId is not the same as the requestedId");
        }
        return ResponseEntity.ok(ResponseUtil.success(commentService.addComment(comment), "comment added successfully", request.getRequestURI()));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ResponseCommentDTO>>> getAllCommentsByPostId(@Valid @RequestParam(name = "postId")int postId, HttpServletRequest request)
    {
        return ResponseEntity.ok(ResponseUtil.success(commentService.getCommentsByPostId(postId), String.format("All comments of blogPost Id %d fetched successfully",postId), request.getRequestURI()));
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponse<ResponseCommentDTO>> updateCommentByCommentId(@Valid @RequestBody UpdateCommentDTO updateCommentDTO, HttpServletRequest request)
    {
        if(!authorizationService.checkIfAuthorizeToUpdateComment(updateCommentDTO.getCommentId(),request))
        {
            throw new AuthorizationException("Do not have authorization to update comment");
        }
        return ResponseEntity.ok(ResponseUtil.success(commentService.updateComment(updateCommentDTO),String.format("Comment with commentId %d successfully updated",updateCommentDTO.getCommentId()), request.getRequestURI()));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Integer>>deleteCommentByCommentId(@Valid @PathVariable int commentId, HttpServletRequest request)
    {
        if(!authorizationService.checkIfAuthorizeToDeleteComment(commentId,request))
        {
            throw new AuthorizationException("Does not have authorization to delete comment");
        }
        return ResponseEntity.ok(ResponseUtil.success(commentService.deleteCommentByCommentId(commentId),String.format("Comment with commentId %d deleted",commentId), request.getRequestURI()));
    }
}
