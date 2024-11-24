package com.Anonymous.smart_printing_system.repository;


import com.Anonymous.smart_printing_system.model.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Long>
{
    Optional<SystemUser> findByEmail(String email);
    Boolean existsByEmail(String email);
}
