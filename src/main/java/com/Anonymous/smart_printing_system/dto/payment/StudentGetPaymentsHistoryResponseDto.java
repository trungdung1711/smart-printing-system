package com.Anonymous.smart_printing_system.dto.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
public class StudentGetPaymentsHistoryResponseDto {
    private List<StudentPaymentDto> studentPaymentDtoList;
    private Integer pageNumCurrent;
    private Integer pageSize;
    private Integer totalPages;
    private String message;
}
