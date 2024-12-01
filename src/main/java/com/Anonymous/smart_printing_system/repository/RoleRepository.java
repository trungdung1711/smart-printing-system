package com.Anonymous.smart_printing_system.repository;


import com.Anonymous.smart_printing_system.model.Role;
import com.Anonymous.smart_printing_system.model.eenum.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>
{
    Role findByName(RoleEnum name);
}
