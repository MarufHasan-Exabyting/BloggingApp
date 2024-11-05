package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.model.User;
import com.example.BloggingApplication.model.UserProfile;
import jakarta.persistence.EntityManager;
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

    //dynamic query should be at a separate function
    @Override
    public UserProfile getUserProfileByName(String name) {
        TypedQuery<UserProfile> query = entityManager.createQuery("From UserProfile where userName = :userProfileName",UserProfile.class);
        query.setParameter("userProfileName",name);
        List<UserProfile> userProfiles = query.getResultList();
        return userProfiles.getFirst();
    }

    @Override
    public UserProfile getUserProfileByUserId(int userid) {
        TypedQuery<UserProfile> query = entityManager.createQuery ("From UserProfile where createdBy = :user", UserProfile.class);
        User user = entityManager.find(User.class,userid);
        query.setParameter("user",user);
        List<UserProfile> userProfiles = query.getResultList();
        return userProfiles.getFirst();
    }

    @Override
    public UserProfile updateUserProfile(UserProfile userProfile) {
        entityManager.merge(userProfile);
        return entityManager.find(UserProfile.class,userProfile.getUserProfileId());
    }

    @Override
    public UserProfile deleteUserProfileByUserId(int userId) {
        return null;
    }
}
