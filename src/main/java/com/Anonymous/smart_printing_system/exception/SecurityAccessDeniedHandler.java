package com.Anonymous.smart_printing_system.exception;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Handler for filters
 * when an authenticated user
 * attempts to access a resource
 * they don’t have permission for
 * Return 403 Forbidden
 */
@Component
public class SecurityAccessDeniedHandler
implements AccessDeniedHandler
{
    @Value("{com.sps.serverName}")
    String serverName;


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException
    {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response
                .getWriter()
                .write("{\"" + serverName +"_AccessDeniedHandler\": \"Access denied\"}");
    }
}
