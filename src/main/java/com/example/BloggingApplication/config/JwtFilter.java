package com.example.BloggingApplication.config;


import com.example.BloggingApplication.model.Role;
import com.example.BloggingApplication.service.BlogUserDetailsService;
import com.example.BloggingApplication.service.JWTService;
import com.example.BloggingApplication.util.Utility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    //To Do
    //Exception Must be handled in case of not matching JWT token
    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext applicationContext;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        Role role = null;


        if(authHeader != null && authHeader.startsWith("Bearer "))
        {
            token = authHeader.substring(7);
            userName = jwtService.extractUserName(token);
        }
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            UserDetails userDetails = applicationContext.getBean(BlogUserDetailsService.class).loadUserByUsername(userName);
            role = jwtService.extractRole(token);

            boolean isAuthorize = Utility.checkRoleAuthroization(role,request.getRequestURI());


            if(jwtService.validateToken(token, userDetails) && isAuthorize)
            {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
