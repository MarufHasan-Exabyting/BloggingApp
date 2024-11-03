package com.example.UserDemo.service;

import com.example.UserDemo.dto.CreatePostDTO;
import com.example.UserDemo.model.BlogPost;

import java.util.List;

public interface BlogService {
    BlogPost createBlogPost(CreatePostDTO blogPost);

    List<BlogPost> getAllPosts();

    List<BlogPost> getPostByTitle(String Title);

    List<BlogPost> getPostByCategory(String Category);

    List<BlogPost> getPostByAuthor(String authorName);

    BlogPost updatePost(BlogPost blogPost);

    void deletePostById(int postId);
}
