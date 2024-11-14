package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dao.BlogDAO;
import com.example.BloggingApplication.dao.UserProfileDAO;
import com.example.BloggingApplication.dto.CreatePostDTO;
import com.example.BloggingApplication.dto.ResponseBlogPostDTO;
import com.example.BloggingApplication.dto.UpdatePostDTO;
import com.example.BloggingApplication.model.BlogPost;
import com.example.BloggingApplication.model.EntityMetadata;
import com.example.BloggingApplication.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogPostServiceImpl implements BlogService{
    private BlogDAO blogDAO;
    private UserProfileDAO userProfileDAO;

    @Autowired
    public BlogPostServiceImpl(BlogDAO blogDAO, UserProfileDAO userProfileDAO) {
        this.blogDAO = blogDAO;
        this.userProfileDAO = userProfileDAO;
    }

    @Override
    @Transactional
    public ResponseBlogPostDTO createBlogPost(CreatePostDTO createPostDTO) {
        UserProfile userProfile = getUserProfileByUserId(createPostDTO.getPostAuthorId());
        BlogPost blogPost = toBlogPost(createPostDTO,userProfile);
        BlogPost createdPost = blogDAO.createPost(blogPost);
        return getResponsePost(createdPost);
    }

    @Override
    public ResponseBlogPostDTO getpostById(int id) {
        BlogPost blogPost = blogDAO.getPostById(id);
        return getResponsePost(blogPost);
    }

    @Override
    public List<ResponseBlogPostDTO> getAllPosts() {
        System.out.println("Service Layer 49 line");
        return getResponsePosts(blogDAO.getAllPosts());
    }

    @Override
    public List<ResponseBlogPostDTO> getAllPosts(String title, String authorName, String category) {
        return getResponsePosts(blogDAO.getAllPosts(title,authorName,category));
    }

    @Override
    @Transactional
    public ResponseBlogPostDTO updatePost(UpdatePostDTO updatePostDTO) {
        BlogPost blogPost = toBlogPost(updatePostDTO);
        BlogPost updatedBlogPost = blogDAO.updatePost(blogPost);
        return getResponsePost(updatedBlogPost);
    }

    @Transactional
    @Override
    public int deletePostById(int postId) {
        return blogDAO.deletePostById(postId);
    }

    //HelperFunction
    private BlogPost toBlogPost(CreatePostDTO createPostDTO, UserProfile userProfile)
    {
        //to Do
        //check if mapper can be used
        BlogPost blogPost = new BlogPost();
        blogPost.setPostTitle(createPostDTO.getPostTitle());
        blogPost.setCategory(createPostDTO.getCategory());
        blogPost.setContent(createPostDTO.getContent());
        blogPost.setPostAuthorId(userProfile);
        EntityMetadata entityMetadata = new EntityMetadata();
        entityMetadata.setCreatedAt(new Date(System.currentTimeMillis()));;
        entityMetadata.setUpdatedAt(new Date(System.currentTimeMillis()));
        entityMetadata.setDeleted(false);
        blogPost.setMetadata(entityMetadata);
        return blogPost;
    }

    //To BlogPost from updatePostDto

    private BlogPost toBlogPost(UpdatePostDTO updatePostDTO)
    {
        int postAuthorId = updatePostDTO.getPostAuthorId();

        BlogPost blogPost = blogDAO.getPostById(updatePostDTO.getPostId());

        blogPost.setPostId(updatePostDTO.getPostId());
        blogPost.setPostTitle(updatePostDTO.getPostTitle());
        blogPost.setPostAuthorId(getUserProfileByUserId(postAuthorId));
        blogPost.setContent(updatePostDTO.getContent());
        blogPost.setCategory(updatePostDTO.getCategory());

        return blogPost;
    }

    private UserProfile getUserProfileByUserId(int userId)
    {
        UserProfile userProfile = userProfileDAO.getUserProfileByUserId(userId);
        if(userProfile == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("UserProfile with UserId %d not found",userId));
        }
        return userProfile;
    }

    private List<ResponseBlogPostDTO> getResponsePosts(List<BlogPost> blogPosts)
    {
        List<ResponseBlogPostDTO> responsePosts = new ArrayList<>();
        for(BlogPost blogPost : blogPosts)
        {
            System.out.println(blogPost);
            responsePosts.add(getResponsePost(blogPost));
        }
        System.out.println(blogPosts.size());
        return responsePosts;
    }

    private ResponseBlogPostDTO getResponsePost(BlogPost blogPost)
    {
        ResponseBlogPostDTO response = new ResponseBlogPostDTO();
        response.setPostId(blogPost.getPostId());
        response.setCategory(blogPost.getCategory());
        response.setContent(blogPost.getContent());
        response.setPostTitle(blogPost.getPostTitle());
        response.setPostAuthorName(blogPost.getPostAuthorId().getUserName());
        response.setCreatedAt(blogPost.getMetadata().getCreatedAt());
        return response;
    }
}
