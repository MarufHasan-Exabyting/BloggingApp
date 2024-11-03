package com.example.UserDemo.dao;

import com.example.UserDemo.model.User;
import com.example.UserDemo.model.UserProfile;
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
        System.out.println("****** " + user);
        query.setParameter("user",user);
        List<UserProfile> userProfiles = query.getResultList();
        return userProfiles.getFirst();
    }

    @Override
    public UserProfile updateUserProfile(UserProfile userProfile) {
        System.out.println("User Profile: "+ userProfile);
        entityManager.merge(userProfile);
        return entityManager.find(UserProfile.class,userProfile.getUserProfileId());
    }

    @Override
    public UserProfile deleteUserProfileByUserId(int userId) {
        return null;
    }
}
