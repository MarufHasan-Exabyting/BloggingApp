package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.model.BlogPost;

import java.util.List;

public interface BlogDAO {
    BlogPost createPost(BlogPost blogPost);

    List<BlogPost> getAllPosts();

    List<BlogPost> getAllPosts(String title, String authorName, String Category);

    List<BlogPost> getPostByCategory(String Category);

    List<BlogPost> getPostByAuthor(String authorName);

    BlogPost updatePost(BlogPost blogPost);

    int deletePostById(int postId);
}
