package com.example.UserDemo.dao;

import com.example.UserDemo.model.BlogPost;

import java.util.List;

public interface BlogDAO {
    BlogPost createPost(BlogPost blogPost);

    List<BlogPost> getAllPosts();

    List<BlogPost> getPostByTitle(String Title);

    List<BlogPost> getPostByCategory(String Category);

    List<BlogPost> getPostByAuthor(String authorName);

    BlogPost updatePost(BlogPost blogPost);

    void deletePostById(int postId);
}
