package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dto.CreatePostDTO;
import com.example.BloggingApplication.dto.ResponseBlogPostDTO;
import com.example.BloggingApplication.dto.UpdatePostDTO;
import com.example.BloggingApplication.model.BlogPost;

import java.util.List;

public interface BlogService {
    ResponseBlogPostDTO createBlogPost(CreatePostDTO blogPost);

    ResponseBlogPostDTO getpostById(int id);

    List<ResponseBlogPostDTO> getAllPosts();

    List<ResponseBlogPostDTO> getAllPosts(String title, String authorName, String category);

    ResponseBlogPostDTO updatePost(UpdatePostDTO blogPost);

    int deletePostById(int postId);
}
