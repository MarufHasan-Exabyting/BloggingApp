package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dao.Common;
import com.example.BloggingApplication.dao.UserDAO;
import com.example.BloggingApplication.dao.UserProfileDAO;
import com.example.BloggingApplication.dto.CreateUserDTO;
import com.example.BloggingApplication.dto.ResponseUserDTO;
import com.example.BloggingApplication.dto.UpdateUserDTO;
import com.example.BloggingApplication.exception.UserCreateException;
import com.example.BloggingApplication.exception.UserNotFoundException;
import com.example.BloggingApplication.model.EntityMetadata;
import com.example.BloggingApplication.model.User;
import com.example.BloggingApplication.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDao;
    private UserProfileDAO userProfileDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDao, UserProfileDAO userProfileDAO) {
        this.userDao = userDao;
        this.userProfileDAO = userProfileDAO;
    }

    @Override
    @Transactional
    public ResponseUserDTO createUser(CreateUserDTO createUserDTO) {

        User createdUser = createUserFromDTO(createUserDTO);
        if(createdUser == null)
        {
            throw new UserCreateException("User not created Exception");
        }
        UserProfile userProfile = createUserProfile(createdUser);
        userProfileDAO.createUserProfile(userProfile);
        return getUserResponseDTO(createdUser);
    }

    @Override
    public List<ResponseUserDTO> getAllUsers() {
        List<User> users =  userDao.getAllUsers();
        List<ResponseUserDTO> responseUserDTOList = new ArrayList<>() ;

        for(User user: users)
        {
            ResponseUserDTO responseUserDTO = getUserResponseDTO(user);
            responseUserDTOList.add(responseUserDTO);
        }
        return responseUserDTOList;
    }

    @Override
    public ResponseUserDTO getUserById(int id) {
       User user = userDao.getUserById(id);

       if(user == null)
       {
           throw new UserNotFoundException("User with UserId: "+id + " not found");
       }

       return getUserResponseDTO(user);
    }

    @Override
    @Transactional
    public ResponseUserDTO updateUser(UpdateUserDTO updateUserDTO) {
        User user = getUserFromUpdateUserDTO(updateUserDTO);
        if (userDao.getUserById(user.getUserId()) == null)
        {
            throw new UserNotFoundException(String.format("User with User ID %d not found",user.getUserId()));
        }
        User updatedUser = userDao.updateUser(user);

        EntityMetadata entityMetadata = Common.getEntityMetadata(updatedUser.getMetadata().getCreatedAt(),new Date(System.currentTimeMillis()));
        entityMetadata.setDeleted(false);
        updatedUser.setMetadata(entityMetadata);

        UserProfile userProfile = userProfileDAO.getUserProfileByUserId(updatedUser.getUserId());
        if(userProfile == null)
        {
            throw new UserNotFoundException(String.format("UserProfile with UserId %d not found.",updatedUser.getUserId()));
        }

        UserProfile updatedUserProfile = getUpdatedUserProfileFromUpdatedUser(userProfile, updatedUser);
        userProfileDAO.updateUserProfile(updatedUserProfile);

        return getUserResponseDTO(updatedUser);
    }

    @Override
    @Transactional
    public int deleteUser(int id) {
        userProfileDAO.deleteUserProfileByUserId(id);
        return userDao.deleteUser(id);
    }

    //Helper functions
    public User createUserFromDTO(CreateUserDTO createUserDTO)
    {
        if(isUserExist(createUserDTO.getUserEmail()))
        {
            throw new UserCreateException(String.format("User with mail %s already exists",createUserDTO.getUserEmail()));
        }

        User user = new User();
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setPassword(createUserDTO.getPassword());
        user.setUserEmail(createUserDTO.getUserEmail());


        EntityMetadata metadata = Common.getEntityMetadata(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        metadata.setDeleted(false);
        user.setMetadata(metadata);

        User createdUser = userDao.createUser(user);
        if(createdUser == null)
        {
            throw new UserCreateException("User Not created Exception");
        }
        return createdUser;
    }

    private boolean isUserExist(String userEmail)
    {
        User user = userDao.getUserByEmail(userEmail);
        if(user == null)
        {
            return false;
        }
        return true;
    }


    private UserProfile createUserProfile(User createdUser) {
        UserProfile userProfile = new UserProfile();
        String userName = createdUser.getFirstName() + " " + createdUser.getLastName();
        userProfile.setUserName(userName);
        userProfile.setUserEmail(createdUser.getUserEmail());
        userProfile.setMetadata(createdUser.getMetadata());
        userProfile.setCreatedBy(createdUser);
        return userProfile;
    }

    private ResponseUserDTO getUserResponseDTO(User createdUser) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setUserId(createdUser.getUserId());
        responseUserDTO.setFirstName(createdUser.getFirstName());
        responseUserDTO.setLastName(createdUser.getLastName());
        responseUserDTO.setUserEmail(createdUser.getUserEmail());
        responseUserDTO.setCreated_at(createdUser.getMetadata().getCreatedAt());
        responseUserDTO.setUpdated_at(createdUser.getMetadata().getUpdatedAt());
        return responseUserDTO;
    }

    private User getUserFromUpdateUserDTO(UpdateUserDTO updateUserDTO)
    {
        User user = new User();
        user.setUserId(updateUserDTO.getUserId());
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setPassword(updateUserDTO.getPassword());
        user.setUserEmail(updateUserDTO.getUserEmail());
        return user;
    }

    private UserProfile getUpdatedUserProfileFromUpdatedUser(UserProfile userProfile, User updatedUser)
    {
        String userName = updatedUser.getFirstName() + " " + updatedUser.getLastName();
        userProfile.setUserName(userName);
        userProfile.setUserEmail(updatedUser.getUserEmail());
        EntityMetadata metadata = userProfile.getMetadata();
        metadata.setUpdatedAt(new Date(System.currentTimeMillis()));
        userProfile.setMetadata(metadata);
        return userProfile;
    }
}
