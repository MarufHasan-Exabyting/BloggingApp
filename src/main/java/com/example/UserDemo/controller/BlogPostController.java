package com.example.UserDemo.controller;

import com.example.UserDemo.dto.CreatePostDTO;
import com.example.UserDemo.model.BlogPost;
import com.example.UserDemo.service.BlogService;
import jakarta.validation.Valid;
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
    public BlogPost createBlogPost(@Valid @RequestBody CreatePostDTO createPostDTO) {
        return blogService.createBlogPost(createPostDTO);
    }

    @GetMapping("/")
    public List<BlogPost> getAllPosts()
    {
        return blogService.getAllPosts();
    }

    @GetMapping("/search")
    public List<BlogPost> getAllPostsByParameters(@RequestParam(value = "authorName", required = false) String authorName,
                                                  @RequestParam(value = "title", required = false) String title,
                                                  @RequestParam(value = "category", required = false) String category) {
        return blogService.getAllPosts(title, authorName, category);
    }

    @PutMapping("/")
    public BlogPost updateBlogPost(BlogPost blogPost)
    {
        return blogService.updatePost(blogPost);
    }

    @DeleteMapping("/{postId}")
    public void deleteBlogPost(@PathVariable int postId)
    {
        blogService.deletePostById(postId);
    }
}
