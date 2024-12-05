package com.Anonymous.smart_printing_system.dto.payment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
public class StudentGetPaymentHistoryRequestDto {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
}
