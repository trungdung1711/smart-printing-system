package com.Anonymous.smart_printing_system.dto.payment;

import com.Anonymous.smart_printing_system.model.Student;
import com.Anonymous.smart_printing_system.model.eenum.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentPaymentDto {
    private Double payCost;
    private LocalDateTime payDate;
    private PaymentStatus status;
    private Long numberOfPages;
}
