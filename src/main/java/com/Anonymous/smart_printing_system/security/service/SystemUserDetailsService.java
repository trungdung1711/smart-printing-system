package com.Anonymous.smart_printing_system.security.service;


import com.Anonymous.smart_printing_system.model.SystemUser;
import com.Anonymous.smart_printing_system.repository.SystemUserRepository;
import com.Anonymous.smart_printing_system.security.model.SystemUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService
{
    private final SystemUserRepository systemUserRepository;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException
    {
        SystemUser systemUser = systemUserRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new SystemUserDetails(systemUser);
    }
}
