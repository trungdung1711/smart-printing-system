package com.Anonymous.smart_printing_system.security.model;


import com.Anonymous.smart_printing_system.model.SystemUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@AllArgsConstructor
@Builder
@Getter
@Setter
public class SystemUserDetails implements UserDetails
{
    private SystemUser systemUser;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return systemUser.getRoles();
    }


    @Override
    public String getPassword()
    {
        return systemUser.getPassword();
    }


    @Override
    public String getUsername()
    {
        return systemUser.getEmail();
    }


    public Long getId()
    {
        return systemUser.getId();
    }
}
