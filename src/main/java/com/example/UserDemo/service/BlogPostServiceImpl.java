package com.example.UserDemo.service;

import com.example.UserDemo.dao.BlogDAO;
import com.example.UserDemo.dao.UserProfileDAO;
import com.example.UserDemo.dto.CreatePostDTO;
import com.example.UserDemo.exception.UserProfileCreateException;
import com.example.UserDemo.model.BlogPost;
import com.example.UserDemo.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public BlogPost createBlogPost(CreatePostDTO createPostDTO) {
        //System.out.println("***** " + createPostDTO);
        UserProfile userProfile = userProfileDAO.getUserProfileByUserId(createPostDTO.getPostAuthorId());
        if(userProfile == null)
        {
            throw new UserProfileCreateException(String.format("UserProfile with UserId %d not found",createPostDTO.getPostAuthorId()));
        }
        BlogPost blogPost = toBlogPost(createPostDTO,userProfile);
        return blogDAO.createPost(blogPost);
    }

    @Override
    public List<BlogPost> getAllPosts() {
        return blogDAO.getAllPosts();
    }

    @Override
    public List<BlogPost> getAllPosts(String title, String authorName, String category) {
        return blogDAO.getAllPosts(title,authorName,category);
    }
    @Override
    public BlogPost updatePost(BlogPost blogPost) {
        return blogDAO.updatePost(blogPost);
    }

    @Override
    public void deletePostById(int postId) {
        blogDAO.deletePostById(postId);
    }

    public BlogPost toBlogPost(CreatePostDTO createPostDTO, UserProfile userProfile)
    {
        BlogPost blogPost = new BlogPost();
        blogPost.setPostTitle(createPostDTO.getPostTitle());
        blogPost.setCategory(createPostDTO.getCategory());
        blogPost.setContent(createPostDTO.getContent());
        blogPost.setPostAuthorId(userProfile);
        return blogPost;
    }
}
