package com.Anonymous.smart_printing_system.dto.mapper;


import com.Anonymous.smart_printing_system.dto.SignUpRequestDto;
import com.Anonymous.smart_printing_system.dto.SignUpResponseDto;
import com.Anonymous.smart_printing_system.model.SystemUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SystemUserMapper
{
    SystemUser toEntity(SignUpRequestDto signUpRequestDto);


    SignUpResponseDto toDto(SystemUser systemUser);
}
