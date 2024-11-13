package com.example.BloggingApplication.controller;

import com.example.BloggingApplication.dto.ApiResponse;
import com.example.BloggingApplication.dto.CreatePostDTO;
import com.example.BloggingApplication.dto.ResponseBlogPostDTO;
import com.example.BloggingApplication.dto.UpdatePostDTO;
import com.example.BloggingApplication.service.BlogService;
import com.example.BloggingApplication.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class BlogPostController {
    private BlogService blogService;

    @Autowired
    public BlogPostController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/blog")
    public ResponseEntity<ApiResponse<ResponseBlogPostDTO> >  createBlogPost(@Valid @RequestBody CreatePostDTO createPostDTO, HttpServletRequest request) {
        ResponseBlogPostDTO createdBlogPost = blogService.createBlogPost(createPostDTO);
        return ResponseEntity.ok(ResponseUtil.success(createdBlogPost,"Blog post created successfully.",request.getRequestURI()));
    }

    @GetMapping("/blog")
    public ResponseEntity<ApiResponse<List<ResponseBlogPostDTO>> > getAllPosts(HttpServletRequest request)
    {
        List<ResponseBlogPostDTO> blogPosts =  blogService.getAllPosts();
        return ResponseEntity.ok(ResponseUtil.success(blogPosts,"Successfully retrieved all blog posts", request.getRequestURI()));
    }

    @GetMapping("/blog/{postId}")

    public ResponseEntity<ApiResponse<ResponseBlogPostDTO>> getPostById(@Valid @PathVariable int postId, HttpServletRequest request)
    {
        ResponseBlogPostDTO responseBlogPostDTO = blogService.getpostById(postId);
        return ResponseEntity.ok(ResponseUtil.success(responseBlogPostDTO,String.format("Blog post with Id %d is retrived",postId), request.getRequestURI()));
    }


    @GetMapping("/blog/search")
    public ResponseEntity<ApiResponse<List<ResponseBlogPostDTO>> > getAllPostsByParameters(@Valid @RequestParam(value = "authorName", required = false) String authorName,
                                                                                           @Valid @RequestParam(value = "title", required = false) String title,
                                                                                           @Valid @RequestParam(value = "category", required = false) String category,
                                                                                           HttpServletRequest request) {

        List<ResponseBlogPostDTO> blogPosts = blogService.getAllPosts(title, authorName, category);
        return ResponseEntity.ok(ResponseUtil.success(blogPosts,"Successfully retrieved all blog posts", request.getRequestURI()));
    }

    @PutMapping("/blog")
    public ResponseEntity<ApiResponse<ResponseBlogPostDTO> > updateBlogPost(@Valid @RequestBody UpdatePostDTO updatePostDTO, HttpServletRequest request)
    {
        ResponseBlogPostDTO blogPost = blogService.updatePost(updatePostDTO);
        return ResponseEntity.ok(ResponseUtil.success(blogPost,"The post successfully updated", request.getRequestURI()));
    }

    @DeleteMapping("/blog/{postId}")
    public ResponseEntity<ApiResponse<Integer>> deleteBlogPost(@Valid @PathVariable int postId, HttpServletRequest request)
    {
        int deletedCount = blogService.deletePostById(postId);
        return ResponseEntity.ok(ResponseUtil.success(deletedCount,String.format("Blogpost with postId : %d successfully deleted",postId), request.getRequestURI()));
    }
}
