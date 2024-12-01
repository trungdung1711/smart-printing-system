package com.Anonymous.smart_printing_system.dto;

import com.Anonymous.smart_printing_system.model.eenum.SexEnum;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.Anonymous.smart_printing_system.model.SystemUser}
 */
@Value
public class SystemUserInformationResponseDto implements Serializable
{
    Long id;
    String email;
    SexEnum sex;
    String lastName;
    String firstName;
    String phoneNumber;
}