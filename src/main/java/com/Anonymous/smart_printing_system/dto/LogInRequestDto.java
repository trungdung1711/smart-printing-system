package com.Anonymous.smart_printing_system.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class LogInRequestDto
{
    @Email
    @Email(message = "Invalid email format")
    @NotNull(message = "Email required")
    @Pattern(
            regexp = "^[\\w.%+-]+@hcmut\\.edu\\.vn$",
            message = "Must be authenticated using HCMUT CAS [Central authentication service]"
    )
    String email;
    @NotNull(message = "Password required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    String password;
}
