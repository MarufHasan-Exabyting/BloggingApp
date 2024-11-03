package com.example.UserDemo.dao;

import com.example.UserDemo.model.UserProfile;

public interface UserProfileDAO {
    UserProfile createUserProfile(UserProfile userProfile);

    UserProfile getUserProfileByName(String name);

    UserProfile getUserProfileByUserId(int userid);

    UserProfile updateUserProfile(UserProfile userProfile);

    UserProfile deleteUserProfileByUserId(int userId);
}
