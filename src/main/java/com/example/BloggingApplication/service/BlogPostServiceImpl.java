package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dao.BlogDAO;
import com.example.BloggingApplication.dao.UserProfileDAO;
import com.example.BloggingApplication.dto.CreatePostDTO;
import com.example.BloggingApplication.dto.ResponseBlogPostDTO;
import com.example.BloggingApplication.dto.UpdatePostDTO;
import com.example.BloggingApplication.exception.BlogPostCreateException;
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

        System.out.println("Before Update: "+ blogPost);

        //update title if not null
        if(updatePostDTO.getPostTitle() != null)
        {
            if(updatePostDTO.getPostTitle().length() >100 || updatePostDTO.getPostTitle().length()<3)
            {
                throw new BlogPostCreateException("The length of the Title should be between 3 to 100");
            }
            blogPost.setPostTitle(updatePostDTO.getPostTitle());
        }

        //update content if not null
        if(updatePostDTO.getContent() != null)
        {
            if(updatePostDTO.getContent().length() > 5000 || updatePostDTO.getContent().length() <5)
            {
                throw new BlogPostCreateException("BlogPost content should be less than 5000 and more than 5 characters");
            }
            blogPost.setContent(updatePostDTO.getContent());
        }

        //update category if not null
        if(updatePostDTO.getCategory() != null)
        {
            blogPost.setCategory(updatePostDTO.getCategory());
        }
        System.out.println("After Update: "+blogPost);
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
            responsePosts.add(getResponsePost(blogPost));
        }

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
