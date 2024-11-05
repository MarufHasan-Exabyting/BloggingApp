package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.model.UserProfile;

public interface UserProfileDAO {
    UserProfile createUserProfile(UserProfile userProfile);

    UserProfile getUserProfileByName(String name);

    UserProfile getUserProfileByUserId(int userid);

    UserProfile updateUserProfile(UserProfile userProfile);

    UserProfile deleteUserProfileByUserId(int userId);
}
