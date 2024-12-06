package com.Anonymous.smart_printing_system.dto.mapper;

import com.Anonymous.smart_printing_system.dto.payment.StudentGetNumPagesDto;
import com.Anonymous.smart_printing_system.dto.payment.StudentGetWalletDto;
import com.Anonymous.smart_printing_system.model.Student;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentGetNumPagesDto studentGetNumPagesDto);

    StudentGetNumPagesDto toDto(Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Student partialUpdate(StudentGetNumPagesDto studentGetNumPagesDto, @MappingTarget Student student);

    Student toEntity(StudentGetWalletDto studentGetWalletDto);

    StudentGetWalletDto toDto1(Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Student partialUpdate(StudentGetWalletDto studentGetWalletDto, @MappingTarget Student student);
}
