package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dao.Common;
import com.example.BloggingApplication.dao.UserDAO;
import com.example.BloggingApplication.dao.UserProfileDAO;
import com.example.BloggingApplication.dto.CreateUserDTO;
import com.example.BloggingApplication.dto.LogInDTO;
import com.example.BloggingApplication.dto.ResponseUserDTO;
import com.example.BloggingApplication.dto.UpdateUserDTO;
import com.example.BloggingApplication.exception.UserCreateException;
import com.example.BloggingApplication.exception.UserNotFoundException;
import com.example.BloggingApplication.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDao;
    private UserProfileDAO userProfileDAO;
    private AuthenticationManager authenticationManager;

    private JWTService jwtService;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);


    @Autowired
    public UserServiceImpl(UserDAO userDao, UserProfileDAO userProfileDAO, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userDao = userDao;
        this.userProfileDAO = userProfileDAO;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public ResponseUserDTO RegisterUser(CreateUserDTO createUserDTO) {
        //System.out.println(createUserDTO);
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
        //check if userName exists
        User userByUserName = userDao.getUserByUserName(updateUserDTO.getUserName());
        if(userByUserName != null && (userByUserName.getUserId() != updateUserDTO.getUserId()))
        {
            System.out.println(userByUserName.getUserId() + " Line 93 " + updateUserDTO.getUserId());
            throw new UserCreateException(String.format("User with User_Name already exists",updateUserDTO.getUserName()));
        }

        User user = getUserFromUpdateUserDTO(updateUserDTO);

        if (userDao.getUserById(user.getUserId()) == null)
        {
            throw new UserNotFoundException(String.format("User with User ID %d not found",user.getUserId()));
        }
        User updatedUser = userDao.updateUser(user);

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

    @Override
    public String verifyLogin(LogInDTO logInDTO) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(logInDTO.getUserName(),logInDTO.getPassword()));
        if(authentication.isAuthenticated())
        {
            return jwtService.generateToken(logInDTO.getUserName());
        }
        return "fail";
    }

    //Helper functions
    public User createUserFromDTO(CreateUserDTO createUserDTO)
    {

        if(isUserExist(createUserDTO.getUserEmail()))
        {
            throw new UserCreateException(String.format("User with mail %s already exists",createUserDTO.getUserEmail()));
        }
        else if(!isUserNameAvailable(createUserDTO.getUserName()))
        {
            throw new UserCreateException(String.format("UserName %s not available",createUserDTO.getUserName()));
        }
        System.out.println("Line 148");
        User user = new User();
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(createUserDTO.getPassword()));
        user.setUserEmail(createUserDTO.getUserEmail());
        user.setUserName(createUserDTO.getUserName());
        user.setRole(Role.ROLE_USER);


        EntityMetadata metadata = Common.getEntityMetadata(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        metadata.setDeleted(false);
        user.setMetadata(metadata);

        User createdUser = userDao.createUser(user);
        if(createdUser == null)
        {
            throw new UserCreateException("User Not created Exception");
        }
        System.out.println("line 166 "+createdUser);
        return createdUser;
    }

    private boolean isUserExist(String userEmail)
    {
        User user = userDao.getUserByEmail(userEmail);
        return (!(user==null));
    }

    private boolean isUserNameAvailable(String userName)
    {
        System.out.println("Line 178 "+userName);
        User user = userDao.getUserByUserName(userName);
        System.out.println("Line 180 "+user);
        return (user == null);
    }


    private UserProfile createUserProfile(User createdUser) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserName(createdUser.getUserName());
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
        responseUserDTO.setUser_name(createdUser.getUserName());
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
        user.setPassword(bCryptPasswordEncoder.encode(updateUserDTO.getPassword()));
        user.setUserEmail(updateUserDTO.getUserEmail());
        user.setUserName(updateUserDTO.getUserName());
        user.setRole(Role.ROLE_USER);
        return user;
    }

    private UserProfile getUpdatedUserProfileFromUpdatedUser(UserProfile userProfile, User updatedUser)
    {
        userProfile.setUserName(updatedUser.getUserName());
        userProfile.setUserEmail(updatedUser.getUserEmail());
        EntityMetadata metadata = userProfile.getMetadata();
        metadata.setUpdatedAt(new Date(System.currentTimeMillis()));
        userProfile.setMetadata(metadata);
        return userProfile;
    }
}
