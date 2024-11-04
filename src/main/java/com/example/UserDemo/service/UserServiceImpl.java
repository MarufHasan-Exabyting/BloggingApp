package com.example.UserDemo.service;

import com.example.UserDemo.dao.UserDAO;
import com.example.UserDemo.dao.UserProfileDAO;
import com.example.UserDemo.dto.CreateUserDTO;
import com.example.UserDemo.dto.ResponseUserDTO;
import com.example.UserDemo.dto.UpdateUserDTO;
import com.example.UserDemo.exception.UserCreateException;
import com.example.UserDemo.exception.UserNotFoundException;
import com.example.UserDemo.model.User;
import com.example.UserDemo.model.UserProfile;
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
        ResponseUserDTO responseUserDTO = getUserResponseDTO(createdUser);
        return responseUserDTO;
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
        System.out.println(id);
       User user = userDao.getUserById(id);
       if(user==null)
       {
           throw new UserNotFoundException("User with UserId: "+id + " not found");
       }
        return getUserResponseDTO(user);
    }

    @Override
    @Transactional
    public ResponseUserDTO updateUser(UpdateUserDTO updateUserDTO) {
        User user = new User();
        user.setUserId(updateUserDTO.getUserId());
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setPassword(updateUserDTO.getPassword());
        user.setUserEmail(updateUserDTO.getUserEmail());
        User updatedUser = userDao.updateUser(user);

        UserProfile userProfile = userProfileDAO.getUserProfileByUserId(updatedUser.getUserId());
        if(userProfile ==null)
        {
            throw new UserNotFoundException(String.format("UserProfile with UserId %d not found.",updatedUser.getUserId()));
        }

        String userName = updatedUser.getFirstName() + " " + updatedUser.getLastName();
        userProfile.setUserName(userName);
        userProfile.setUserEmail(updatedUser.getUserEmail());
        userProfile.setUpdatedAt(new Date(System.currentTimeMillis()));
        userProfileDAO.updateUserProfile(userProfile);

        return getUserResponseDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }


    //Helper functions
    public User createUserFromDTO(CreateUserDTO createUserDTO)
    {
        User user = new User();
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setPassword(createUserDTO.getPassword());
        user.setUserEmail(createUserDTO.getUserEmail());
        user.setCreatedAt( new Date(System.currentTimeMillis()));
        user.setUpdatedAt( new Date(System.currentTimeMillis()));

        User createdUser = userDao.createUser(user);
        if(createdUser == null)
        {
            throw new UserCreateException("User Not created Exception");
        }
        return createdUser;
    }

    private UserProfile createUserProfile(User createdUser) {
        UserProfile userProfile = new UserProfile();
        String userName = createdUser.getFirstName() + " " + createdUser.getLastName();
        userProfile.setUserName(userName);
        userProfile.setUserEmail(createdUser.getUserEmail());
        userProfile.setCreatedAt(createdUser.getCreatedAt());
        userProfile.setUpdatedAt(createdUser.getUpdatedAt());
        userProfile.setCreatedBy(createdUser);
        return userProfile;
    }

    private ResponseUserDTO getUserResponseDTO(User createdUser) {
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setUserId(createdUser.getUserId());
        responseUserDTO.setFirstName(createdUser.getFirstName());
        responseUserDTO.setLastName(createdUser.getLastName());
        responseUserDTO.setUserEmail(createdUser.getUserEmail());
        responseUserDTO.setCreated_at(createdUser.getCreatedAt());
        responseUserDTO.setUpdated_at(createdUser.getUpdatedAt());
        return responseUserDTO;
    }
}
