package com.Anonymous.smart_printing_system.dto.payment;

import com.Anonymous.smart_printing_system.model.eenum.PaymentStatus;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.Anonymous.smart_printing_system.model.Payment}
 */
@Value
public class PaymentDto implements Serializable {
    Double payCost;
    LocalDateTime payDate;
    PaymentStatus status;
    Long numberOfPages;
    Long studentStudentId;
}