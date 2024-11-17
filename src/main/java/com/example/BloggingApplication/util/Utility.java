package com.example.BloggingApplication.util;

import com.example.BloggingApplication.config.JwtFilter;
import com.example.BloggingApplication.model.Role;
import com.example.BloggingApplication.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class Utility {

    public static String getTokenFromRequest(HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if(authHeader != null && authHeader.startsWith("Bearer "))
        {
            token = authHeader.substring(7);
        }
        return token;
    }

    public static boolean checkRoleAuthroization(Role role, String url)
    {
        System.out.println(role.toString() + " " + url);
        if(url.contains("admin") && role != Role.ROLE_ADMIN)
        {
            return false;
        }
        return true;
    }
}
