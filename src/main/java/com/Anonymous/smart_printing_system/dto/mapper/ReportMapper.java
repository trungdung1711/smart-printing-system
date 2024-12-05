package com.Anonymous.smart_printing_system.dto.mapper;

import com.Anonymous.smart_printing_system.dto.ReportGetAllReportsResponseDto;
import com.Anonymous.smart_printing_system.model.Report;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {
    Report toEntity(ReportGetAllReportsResponseDto reportGetAllReportsResponseDto);

    ReportGetAllReportsResponseDto toDto(Report report);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Report partialUpdate(ReportGetAllReportsResponseDto reportGetAllReportsResponseDto, @MappingTarget Report report);

}