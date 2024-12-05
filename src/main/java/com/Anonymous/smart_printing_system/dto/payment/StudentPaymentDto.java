package com.Anonymous.smart_printing_system.dto.payment;

import com.Anonymous.smart_printing_system.model.Student;
import com.Anonymous.smart_printing_system.model.eenum.PaymentStatus;
import jakarta.persistence.*;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class StudentPaymentDto {
    private Double payCost;
    private LocalDateTime payDate;
    private PaymentStatus status;
    private Long numberOfPages;
}
