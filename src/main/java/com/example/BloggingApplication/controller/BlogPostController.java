package com.example.BloggingApplication.controller;

import com.example.BloggingApplication.dto.ApiResponse;
import com.example.BloggingApplication.dto.CreatePostDTO;
import com.example.BloggingApplication.dto.UpdatePostDTO;
import com.example.BloggingApplication.model.BlogPost;
import com.example.BloggingApplication.service.BlogService;
import com.example.BloggingApplication.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<BlogPost> >  createBlogPost(@Valid @RequestBody CreatePostDTO createPostDTO, HttpServletRequest request) {
        BlogPost createdBlogPost = blogService.createBlogPost(createPostDTO);
        return ResponseEntity.ok(ResponseUtil.success(createdBlogPost,"Blog post created successfully.",request.getRequestURI()));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<BlogPost>> > getAllPosts(HttpServletRequest request)
    {
        List<BlogPost> blogPosts =  blogService.getAllPosts();
        return ResponseEntity.ok(ResponseUtil.success(blogPosts,"Successfully retrieved all blog posts", request.getRequestURI()));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BlogPost>> > getAllPostsByParameters(@Valid @RequestParam(value = "authorName", required = false) String authorName,
                                                  @Valid @RequestParam(value = "title", required = false) String title,
                                                  @Valid @RequestParam(value = "category", required = false) String category,
                                                  HttpServletRequest request) {

        List<BlogPost> blogPosts = blogService.getAllPosts(title, authorName, category);
        return ResponseEntity.ok(ResponseUtil.success(blogPosts,"Successfully retrieved all blog posts", request.getRequestURI()));
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponse<BlogPost> > updateBlogPost(@Valid @RequestBody UpdatePostDTO updatePostDTO, HttpServletRequest request)
    {
        BlogPost blogPost = blogService.updatePost(updatePostDTO);
        return ResponseEntity.ok(ResponseUtil.success(blogPost,"The post successfully updated", request.getRequestURI()));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Integer>> deleteBlogPost(@Valid @PathVariable int postId, HttpServletRequest request)
    {
        int deletedCount = blogService.deletePostById(postId);
        return ResponseEntity.ok(ResponseUtil.success(deletedCount,String.format("Blogpost with postId : %d successfully deleted",postId), request.getRequestURI()));
    }
}
