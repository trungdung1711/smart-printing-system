package com.Anonymous.smart_printing_system.controller;


import com.Anonymous.smart_printing_system.model.Role;
import com.Anonymous.smart_printing_system.model.SystemUser;
import com.Anonymous.smart_printing_system.model.eenum.RoleEnum;
import com.Anonymous.smart_printing_system.repository.RoleRepository;
import com.Anonymous.smart_printing_system.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
@RequiredArgsConstructor
public class TestController
{
    public final RoleRepository roleRepository;


    public final PasswordEncoder passwordEncoder;
    private final SystemUserRepository systemUserRepository;


//    @PostMapping("roles")
//    @ResponseStatus(HttpStatus.CREATED)
//    void setUpRoleData()
//    {
//        Role adminRole = new Role();
//        adminRole.setName(RoleEnum.ROLE_ADMIN);
//        roleRepository.save(adminRole);
//
//        Role spsoRole = new Role();
//        spsoRole.setName(RoleEnum.ROLE_SPSO);
//        roleRepository.save(spsoRole);
//
//        Role studentRole = new Role();
//        studentRole.setName(RoleEnum.ROLE_STUDENT);
//        roleRepository.save(studentRole);
//    }


//    @PostMapping("admin")
//    @ResponseStatus(HttpStatus.CREATED)
//    @Transactional
//    void createAdminAccount()
//    {
//        SystemUser admin = new SystemUser();
//        admin.setEmail("admin@hcmut.edu.vn");
//        admin.setPassword(passwordEncoder.encode("Admin241@"));
//        admin.setLastName("Admin");
//        admin.setFirstName("Admin");
//        admin.setPhoneNumber("19001099");
//
//        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN);
//
//        admin.getRoles().add(adminRole);
//        systemUserRepository.save(admin);
//    }
}
