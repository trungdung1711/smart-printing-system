package com.Anonymous.smart_printing_system.util;


import com.Anonymous.smart_printing_system.security.model.SystemUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemUserUtil
{
    public Long getAuthenticatedUserId() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails) {
            return ((SystemUserDetails) principal).getId();
        } else {
            throw new IllegalStateException("User is not authenticated");
        }
    }
}
