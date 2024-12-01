package com.Anonymous.smart_printing_system.dto;


import lombok.Value;
import java.util.Set;
import com.Anonymous.smart_printing_system.model.Role;


@Value
public class LogInResponseDto
{
    String JWTToken;
    Long id;
    String email;
    String lastName;
    String firstName;
    Set<Role> roles;
}
