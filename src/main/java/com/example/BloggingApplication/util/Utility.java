package com.example.BloggingApplication.util;

import com.example.BloggingApplication.model.Role;
import jakarta.servlet.http.HttpServletRequest;

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
