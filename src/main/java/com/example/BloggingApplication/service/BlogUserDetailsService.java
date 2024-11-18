package com.example.BloggingApplication.service;

import com.example.BloggingApplication.dao.UserDAO;
import com.example.BloggingApplication.exception.UserNotFoundException;
import com.example.BloggingApplication.model.User;
import com.example.BloggingApplication.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BlogUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByUserName(username);
        if(user == null)
        {
            throw new UsernameNotFoundException(String.format("User with userName %s not found",username));
        }
        return new UserPrincipal(user);
    }
}
