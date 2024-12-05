package com.Anonymous.smart_printing_system.dto.mapper;

import com.Anonymous.smart_printing_system.dto.SemesterConfigurationGetConfigurationResponseDto;
import com.Anonymous.smart_printing_system.dto.SemesterConfigurationRequestDto;
import com.Anonymous.smart_printing_system.model.SemesterConfiguration;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SemesterConfigurationMapper {
    SemesterConfiguration toEntity(SemesterConfigurationRequestDto semesterConfigurationRequestDto);

    SemesterConfigurationRequestDto toDto(SemesterConfiguration semesterConfiguration);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SemesterConfiguration partialUpdate(SemesterConfigurationRequestDto semesterConfigurationRequestDto, @MappingTarget SemesterConfiguration semesterConfiguration);

    SemesterConfiguration toEntity(SemesterConfigurationGetConfigurationResponseDto semesterConfigurationGetConfigurationResponseDto);

    SemesterConfigurationGetConfigurationResponseDto toDto1(SemesterConfiguration semesterConfiguration);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SemesterConfiguration partialUpdate(SemesterConfigurationGetConfigurationResponseDto semesterConfigurationGetConfigurationResponseDto, @MappingTarget SemesterConfiguration semesterConfiguration);
}