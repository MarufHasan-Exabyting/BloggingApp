package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.exception.UserNotFoundException;
import com.example.BloggingApplication.exception.UserProfileNotFoundException;
import com.example.BloggingApplication.model.User;
import com.example.BloggingApplication.model.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        TypedQuery<UserProfile> typedQuery = entityManager.createQuery ("From UserProfile where createdBy = :user", UserProfile.class);
        typedQuery.setParameter("user",user);
        List<UserProfile> userProfiles = typedQuery.getResultList();
        if(userProfiles.isEmpty())
        {
            return null;
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
        TypedQuery<UserProfile> userProfileTypedQuery = (entityManager.createQuery("From UserProfile where createdBy = :deletedUser",UserProfile.class));
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
        Query blogPostDeleteQuery = entityManager.createQuery("Delete From BlogPost where postAuthorId = : deletedUserProfile");
        blogPostDeleteQuery.setParameter("deletedUserProfile",userProfile);
        blogPostDeleteQuery.executeUpdate();
    }

    private void deleteUserProfileByUser(User user)
    {
        Query typedquery = entityManager.createQuery("Delete From UserProfile where createdBy = : deletedUser");
        typedquery.setParameter("deletedUser",user);
        typedquery.executeUpdate();
    }
}
