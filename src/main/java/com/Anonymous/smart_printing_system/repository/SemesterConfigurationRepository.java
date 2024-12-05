package com.Anonymous.smart_printing_system.repository;


import com.Anonymous.smart_printing_system.model.SemesterConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SemesterConfigurationRepository extends JpaRepository<SemesterConfiguration, Long>
{

}
