package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dto.CreatePostDTO;
import com.example.BloggingApplication.dto.UpdatePostDTO;
import com.example.BloggingApplication.model.BlogPost;

import java.util.List;

public interface BlogService {
    BlogPost createBlogPost(CreatePostDTO blogPost);

    List<BlogPost> getAllPosts();

    List<BlogPost> getAllPosts(String title, String authorName, String category);

    BlogPost updatePost(UpdatePostDTO blogPost);

    int deletePostById(int postId);
}
