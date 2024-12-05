package com.Anonymous.smart_printing_system.dto.payment;

import com.Anonymous.smart_printing_system.model.Student;
import com.Anonymous.smart_printing_system.model.eenum.PaymentStatus;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PaymentDto {
    private Double payCost;;
    private LocalDateTime payDate;
    private PaymentStatus status;
    private Long numberOfPages;
    private Student student;
}
