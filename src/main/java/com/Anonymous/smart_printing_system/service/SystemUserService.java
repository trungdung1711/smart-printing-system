package com.Anonymous.smart_printing_system.service;


import com.Anonymous.smart_printing_system.dto.SignUpRequestDto;
import com.Anonymous.smart_printing_system.dto.SignUpResponseDto;
import com.Anonymous.smart_printing_system.dto.mapper.SystemUserMapper;
import com.Anonymous.smart_printing_system.exception.UserAlreadyExistsException;
import com.Anonymous.smart_printing_system.model.Role;
import com.Anonymous.smart_printing_system.model.SystemUser;
import com.Anonymous.smart_printing_system.model.eenum.RoleEnum;
import com.Anonymous.smart_printing_system.repository.RoleRepository;
import com.Anonymous.smart_printing_system.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class SystemUserService
{
    private final SystemUserMapper systemUserMapper;


    private final RoleRepository roleRepository;


    private final SystemUserRepository systemUserRepository;


    private final PasswordEncoder passwordEncoder;


    public SignUpResponseDto createSPSO(SignUpRequestDto signUpRequestDto)
    {
        if (systemUserRepository.existsByEmail(signUpRequestDto.getEmail()))
        {
            throw new UserAlreadyExistsException("The email is already used");
        }
        SystemUser systemUser = systemUserMapper.toEntity(signUpRequestDto);
        systemUser.setPassword(passwordEncoder.encode(systemUser.getPassword()));

        Role roleSPSO = roleRepository
                .findByName(RoleEnum.ROLE_SPSO);

        systemUser
                .getRoles()
                .add(roleSPSO);

        SystemUser savedWebUser = systemUserRepository.save(systemUser);
        return systemUserMapper.toDto(savedWebUser);
    }
}
