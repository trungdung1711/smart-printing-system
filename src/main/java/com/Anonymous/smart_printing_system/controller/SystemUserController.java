package com.Anonymous.smart_printing_system.controller;


import com.Anonymous.smart_printing_system.dto.SignUpRequestDto;
import com.Anonymous.smart_printing_system.dto.SignUpResponseDto;
import com.Anonymous.smart_printing_system.dto.SystemUserInformationResponseDto;
import com.Anonymous.smart_printing_system.service.SystemUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class SystemUserController
{
    private final SystemUserService systemUserService;


    @PostMapping("spsos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SignUpResponseDto> createSPSO(@RequestBody @Valid SignUpRequestDto signUpRequestDto)
    {
        SignUpResponseDto signUpResponseDto = systemUserService.createSPSO(signUpRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(signUpResponseDto);
    }


    @GetMapping("info/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.getId()")
    public SystemUserInformationResponseDto getUserInformation(@PathVariable Long id)
    {
        return systemUserService.getUserInformation(id);
    }

    @GetMapping("spsos/get_semester_config")
    @PreAuthorize("hasRole('SPSO')")
    public ResponseEntity<Void> getSemesterConfig()
    {
        return null;
    }

    @GetMapping("spsos/set_semester_config")
    @PreAuthorize("hasRole('SPSO')")
    public ResponseEntity<Void> setSemesterConfig()
    {
        return null;
    }
}
