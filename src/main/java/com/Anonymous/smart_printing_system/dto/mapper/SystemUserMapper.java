package com.Anonymous.smart_printing_system.dto.mapper;


import com.Anonymous.smart_printing_system.dto.SystemUserInformationResponseDto;
import com.Anonymous.smart_printing_system.dto.SignUpRequestDto;
import com.Anonymous.smart_printing_system.dto.SignUpResponseDto;
import com.Anonymous.smart_printing_system.model.SystemUser;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SystemUserMapper
{
    SystemUser toEntity(SignUpRequestDto signUpRequestDto);

    SignUpResponseDto toDto(SystemUser systemUser);

    SystemUser toEntity(SystemUserInformationResponseDto systemUserInformationResponseDto);

    SystemUserInformationResponseDto toInformationDto(SystemUser systemUser);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SystemUser partialUpdate(SystemUserInformationResponseDto systemUserInformationResponseDto, @MappingTarget SystemUser systemUser);
}
