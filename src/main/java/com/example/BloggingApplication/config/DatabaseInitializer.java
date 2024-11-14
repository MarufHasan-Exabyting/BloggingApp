package com.example.BloggingApplication.config;

import com.example.BloggingApplication.dao.UserDAO;
import com.example.BloggingApplication.dao.UserProfileDAO;
import com.example.BloggingApplication.model.EntityMetadata;
import com.example.BloggingApplication.model.Role;
import com.example.BloggingApplication.model.User;
import com.example.BloggingApplication.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    UserDAO userDAO;

    @Autowired
    UserProfileDAO userProfileDAO;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        EntityMetadata entityMetadata = new EntityMetadata();
        entityMetadata.setCreatedAt(new Date(System.currentTimeMillis()));
        entityMetadata.setDeleted(false);

        User admin = new User("admin","admin","admin",new BCryptPasswordEncoder().encode("17899871"),"admin@exabyting.com", Role.ROLE_ADMIN,entityMetadata);
        User existingUser = userDAO.getUserByUserName("admin");
        if(existingUser == null)
        {
            System.out.println("Here in Database line 63" + admin);
            admin = userDAO.createUser(admin);
            UserProfile adminUserProfile = createUserProfile(admin);
            userProfileDAO.createUserProfile(adminUserProfile);
        }
        else
        {
            System.out.println("Admin already exists");
        }
    }

    private UserProfile createUserProfile(User createdUser) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserName(createdUser.getUserName());
        userProfile.setUserEmail(createdUser.getUserEmail());
        userProfile.setMetadata(createdUser.getMetadata());
        userProfile.setCreatedBy(createdUser);
        return userProfile;
    }
}
