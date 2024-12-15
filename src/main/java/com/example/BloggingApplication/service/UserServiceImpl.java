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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

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
        User UpdatingUser = userDao.getUserByUserName(updateUserDTO.getUserName());

        if(UpdatingUser == null)
        {
            throw new UserNotFoundException(String.format("User with UserName %s not found",updateUserDTO.getUserName()));
        }

        //check if both of their UserId is same.
        if((UpdatingUser.getUserId() != updateUserDTO.getUserId()))
        {
            throw new UserCreateException(String.format("User with User_Name already exists",updateUserDTO.getUserName()));
        }


        User UpdatedUserFromDto = getUserFromUpdateUserDTO(updateUserDTO,UpdatingUser);


        User updatedUser = userDao.updateUser(UpdatedUserFromDto);


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
    public Map<String,String> verifyLogin(LogInDTO logInDTO) {
        Map<String, String> loginResponse = new HashMap<>();
        try
        {
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(logInDTO.getUserName(),logInDTO.getPassword()));

            if(authentication.isAuthenticated())
            {
                String jwtToken = jwtService.generateToken(logInDTO.getUserName());
                loginResponse.put("Jwt Token ",jwtToken);
            }
        }
        catch (AuthenticationException authenticationException)
        {
            throw new com.example.BloggingApplication.exception.AuthenticationException(authenticationException.getMessage());
        }
        return loginResponse;
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
        return createdUser;
    }

    private boolean isUserExist(String userEmail)
    {
        User user = userDao.getUserByEmail(userEmail);
        return (!(user==null));
    }

    private boolean isUserNameAvailable(String userName)
    {
        User user = userDao.getUserByUserName(userName);
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

    private User getUserFromUpdateUserDTO(UpdateUserDTO updateUserDTO, User updatingUser)
    {

        if(updateUserDTO.getFirstName() != null)
        {
            updatingUser.setFirstName(updateUserDTO.getFirstName());
        }
        if(updateUserDTO.getLastName() !=null)
        {
            updatingUser.setLastName(updateUserDTO.getLastName());
        }
        if(updateUserDTO.getPassword() != null)
        {
            int lengthOfPassword = updateUserDTO.getPassword().length();
            //check password length
            if(lengthOfPassword <6 || lengthOfPassword >20)
            {
                throw new UserNotFoundException("Password must be between 6-20 character");
            }
            updatingUser.setPassword(bCryptPasswordEncoder.encode(updateUserDTO.getPassword()));
        }

        updatingUser.getMetadata().setUpdatedAt(new Date(System.currentTimeMillis()));

        return updatingUser;
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
