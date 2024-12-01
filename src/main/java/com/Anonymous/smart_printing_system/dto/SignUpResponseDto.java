package com.Anonymous.smart_printing_system.dto;

import lombok.Value;

import java.io.Serializable;


@Value
public class SignUpResponseDto implements Serializable
{
    String email;
    String lastName;
    String firstName;
}