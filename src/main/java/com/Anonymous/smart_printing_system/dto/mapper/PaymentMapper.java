package com.Anonymous.smart_printing_system.dto.mapper;

import com.Anonymous.smart_printing_system.dto.payment.StudentPaymentDto;
import com.Anonymous.smart_printing_system.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    StudentPaymentDto toStudentPaymentDto(Payment payment);
}
