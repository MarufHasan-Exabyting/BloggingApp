package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.exception.BlogPostNotFoundException;
import com.example.BloggingApplication.exception.UserNotFoundException;
import com.example.BloggingApplication.exception.UserProfileNotFoundException;
import com.example.BloggingApplication.model.BlogPost;
import com.example.BloggingApplication.model.User;
import com.example.BloggingApplication.model.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public class UserProfileDAOImpl implements UserProfileDAO{
    private EntityManager entityManager;

    @Autowired
    public UserProfileDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserProfile createUserProfile(UserProfile userProfile) {
        entityManager.persist(userProfile);
        return userProfile;
    }

    @Override
    public UserProfile getUserProfileByName(String name) {
        String query = Common.getDynamicQuery(UserProfile.class, "userName",name);
        TypedQuery<UserProfile> typedquery = entityManager.createQuery(query,UserProfile.class);
        return  typedquery.getSingleResult();
    }

    @Override
    public UserProfile getUserProfileByUserId(int userId) {
        User user = entityManager.find(User.class,userId);
        TypedQuery<UserProfile> typedQuery = entityManager.createQuery ("From UserProfile where createdBy = :user and metadata.isDeleted = false", UserProfile.class);
        typedQuery.setParameter("user",user);
        List<UserProfile> userProfiles = typedQuery.getResultList();
        if(userProfiles.isEmpty())
        {
            throw new UserProfileNotFoundException(String.format("UserProfile with User Id : %d not found",userId));
        }
        else
        {
            return userProfiles.getFirst();
        }
    }

    @Override
    public UserProfile updateUserProfile(UserProfile userProfile) {
        entityManager.merge(userProfile);
        return entityManager.find(UserProfile.class,userProfile.getUserProfileId());
    }

    @Override
    public void deleteUserProfileByUserId(int userId) {
        //First the related blogposts need
        //to be deleted as userprofile is used
        //as a foreign key in blogpost

        //get user
        User deletedUser = getUserByUserId(userId);

        //get userProfile
        UserProfile userProfile = getUserProfileByUser(deletedUser);

        //delete blogPost

        deleteBlogPostByUserProfile(userProfile);

        //delete UserProfile
        deleteUserProfileByUser(deletedUser);
    }

    //Helper class
    //get user by userId

    private User getUserByUserId(int userId)
    {
        //System.out.println("Get User BY User Id line 88");
        String query = Common.getDynamicQuery(User.class,"userId",userId);
        List<User> deletedUsers = (entityManager.createQuery(query, User.class)).getResultList();
        if(deletedUsers.isEmpty())
        {
            throw new UserNotFoundException(String.format("User with User Id : %d already deleted",userId));
        }
        return deletedUsers.getFirst();
    }

    private UserProfile getUserProfileByUser(User deletedUser)
    {
        System.out.println("Here getUserPrfileByUser ");
        TypedQuery<UserProfile> userProfileTypedQuery = (entityManager.createQuery("From UserProfile where createdBy = :deletedUser and metadata.isDeleted = false",UserProfile.class));
        userProfileTypedQuery.setParameter("deletedUser",deletedUser);
        List<UserProfile> userProfiles =  userProfileTypedQuery.getResultList();
        if(userProfiles == null)
        {
            throw new UserProfileNotFoundException(String.format("User Profile with User Id : %d not found",deletedUser.getUserId()));
        }
        return userProfiles.getFirst();
    }

    private void deleteBlogPostByUserProfile(UserProfile userProfile)
    {
        TypedQuery<BlogPost> blogPostDeleteQuery = entityManager.createQuery("From BlogPost where postAuthorId = : deletedUserProfile",BlogPost.class);
        blogPostDeleteQuery.setParameter("deletedUserProfile",userProfile);
        List<BlogPost> blogPosts = blogPostDeleteQuery.getResultList();
        if(blogPosts.isEmpty())
        {
            return;
        }
        else
        {
            for(BlogPost blogPost : blogPosts)
            {
                blogPost.getMetadata().setDeleted(true);
                blogPost.getMetadata().setDeletedAt(new Date(System.currentTimeMillis()));
            }
        }
    }

    private void deleteUserProfileByUser(User user)
    {
        TypedQuery<UserProfile> typedquery = entityManager.createQuery("From UserProfile where createdBy = : deletedUser", UserProfile.class);
        typedquery.setParameter("deletedUser",user);
        List<UserProfile> userProfiles = typedquery.getResultList();
        if(userProfiles.isEmpty())
        {
            return;
            //throw new UserProfileNotFoundException(String.format("UserProfile with User Id %d not found",user.getUserId()));
        }
        else
        {
            UserProfile userProfile = userProfiles.getFirst();
            userProfile.getMetadata().setDeleted(true);
            userProfile.getMetadata().setDeletedAt(new Date(System.currentTimeMillis()));
        }
    }
}
