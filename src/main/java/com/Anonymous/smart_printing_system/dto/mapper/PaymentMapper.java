package com.Anonymous.smart_printing_system.dto.mapper;

import com.Anonymous.smart_printing_system.dto.payment.PaymentDto;
import com.Anonymous.smart_printing_system.dto.payment.StudentPaymentDto;
import com.Anonymous.smart_printing_system.model.Payment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    StudentPaymentDto toStudentPaymentDto(Payment payment);


    @Mapping(source = "studentStudentId", target = "student.studentId")
    Payment toEntity(PaymentDto paymentDto);

    @Mapping(source = "student.studentId", target = "studentStudentId")
    PaymentDto toDto(Payment payment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "studentStudentId", target = "student.studentId")
    Payment partialUpdate(PaymentDto paymentDto, @MappingTarget Payment payment);
}
