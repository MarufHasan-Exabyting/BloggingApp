package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.model.BlogPost;
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
        //String query = Common.getDynamicQuery(UserProfile.class,"createdBy",user);
        //System.out.println("User Id "+userId);
        //System.out.println("Query: "+query);
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
        //get user
        String query = Common.getDynamicQuery(User.class,"userId",userId);
        User deletedUser = (entityManager.createQuery(query, User.class)).getSingleResult();

        //get userProfile
        TypedQuery<UserProfile> userProfileTypedQuery = (entityManager.createQuery("From UserProfile where createdBy = :deletedUser",UserProfile.class));
        userProfileTypedQuery.setParameter("deletedUser",deletedUser);
        UserProfile userProfile =  userProfileTypedQuery.getSingleResult();

        //delete blogPost
        Query blogPostDeleteQuery = entityManager.createQuery("Delete From BlogPost where postAuthorId = : deletedUserProfile");
        blogPostDeleteQuery.setParameter("deletedUserProfile",userProfile);
        blogPostDeleteQuery.executeUpdate();

        //delete UserProfile
        Query typedquery = entityManager.createQuery("Delete From UserProfile where createdBy = : deletedUser");
        typedquery.setParameter("deletedUser",deletedUser);
        typedquery.executeUpdate();
        System.out.println("Done userProfile delete");
    }
}
