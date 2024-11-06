package com.example.BloggingApplication.controller;

import com.example.BloggingApplication.dto.CreatePostDTO;
import com.example.BloggingApplication.dto.UpdatePostDTO;
import com.example.BloggingApplication.model.BlogPost;
import com.example.BloggingApplication.service.BlogService;
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

    //ok
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
    public List<BlogPost> getAllPostsByParameters(@Valid @RequestParam(value = "authorName", required = false) String authorName,
                                                  @Valid @RequestParam(value = "title", required = false) String title,
                                                  @Valid @RequestParam(value = "category", required = false) String category) {
        return blogService.getAllPosts(title, authorName, category);
    }

    @PutMapping("/")
    public BlogPost updateBlogPost(@Valid @RequestBody UpdatePostDTO updatePostDTO)
    {
        return blogService.updatePost(updatePostDTO);
    }

    @DeleteMapping("/{postId}")
    public void deleteBlogPost(@Valid @PathVariable int postId)
    {
        blogService.deletePostById(postId);
    }
}
