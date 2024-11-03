package com.example.UserDemo.controller;

import com.example.UserDemo.dto.CreatePostDTO;
import com.example.UserDemo.model.BlogPost;
import com.example.UserDemo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogPostController {
    private BlogService blogService;

    @Autowired
    public BlogPostController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/")
    public BlogPost createBlogPost(@RequestBody  CreatePostDTO createPostDTO)
    {
        return blogService.createBlogPost(createPostDTO);
    }

    @GetMapping("/{authorName}")
    public List<BlogPost> getBlogPostByUserProfileId(@PathVariable String authorName)
    {
        System.out.println("Author Name : "+ authorName);
        return blogService.getPostByAuthor(authorName);
    }
}
