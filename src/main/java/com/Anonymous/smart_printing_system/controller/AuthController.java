package com.Anonymous.smart_printing_system.controller;


import com.Anonymous.smart_printing_system.dto.LogInRequestDto;
import com.Anonymous.smart_printing_system.dto.LogInResponseDto;
import com.Anonymous.smart_printing_system.dto.SignUpRequestDto;
import com.Anonymous.smart_printing_system.dto.SignUpResponseDto;
import com.Anonymous.smart_printing_system.model.SystemUser;
import com.Anonymous.smart_printing_system.security.model.SystemUserDetails;
import com.Anonymous.smart_printing_system.security.util.JwtUtil;
import com.Anonymous.smart_printing_system.service.SystemUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/auth/")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthenticationManager authenticationManager;


    private final JwtUtil jwtUtil;
    private final SystemUserService systemUserService;


    @PostMapping("login")
    public ResponseEntity<LogInResponseDto> logIn(@RequestBody @Valid LogInRequestDto logInRequestDto)
    {
        Authentication authentication = new UsernamePasswordAuthenticationToken(logInRequestDto.getEmail(), logInRequestDto.getPassword());
        authentication = authenticationManager.authenticate(authentication);

        SystemUser systemUser = ((SystemUserDetails) authentication.getPrincipal()).getSystemUser();


        String token = jwtUtil.generateToken(
                systemUser.getId(),
                systemUser.getEmail(),
                systemUser.getRoles());

        LogInResponseDto logInResponseDto = new LogInResponseDto(
                token,
                systemUser.getId(),
                systemUser.getEmail(),
                systemUser.getLastName(),
                systemUser.getFirstName(),
                systemUser.getRoles());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(logInResponseDto);
    }


//    @PostMapping("sign-up")
//    public ResponseEntity<SignUpResponseDto> registerNewWebUser(@RequestBody SignUpRequestDto signUpRequestDto)
//    {
//        SignUpResponseDto signUpResponseDto = systemUserService.createStudent(signUpRequestDto);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(signUpResponseDto);
//    }
}