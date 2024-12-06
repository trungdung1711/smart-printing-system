package com.Anonymous.smart_printing_system.controller;

import com.Anonymous.smart_printing_system.dto.payment.*;
import com.Anonymous.smart_printing_system.service.PaymentService;
import com.Anonymous.smart_printing_system.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("api/v1/payments/")
@RequiredArgsConstructor

public class PaymentController {
    @Autowired
    private final PaymentService paymentService;

    @Autowired
    private final StudentService studentService;

    @PostMapping("buy_pages")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<PaymentBuyPagesResponseDto> studentBuyPages(@RequestBody PaymentBuyPagesRequestDto paymentBuyPagesRequestDto) {
        PaymentBuyPagesResponseDto response = paymentService.studentBuyPagesRequest(paymentBuyPagesRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("add_wallet")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<PaymentAddWalletResponseDto> studentAddWallet(@RequestParam(defaultValue = "0") long amount) {
        try {
            PaymentAddWalletResponseDto response = paymentService.studentAddWallet(amount);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PaymentAddWalletResponseDto(e.getMessage()));
        }
    }

    @GetMapping("get_wallet")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentGetWalletDto> studentGetWallet() {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.studentGetWallet());
    }


    @GetMapping("history/student_buy_pages")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentGetPaymentsHistoryResponseDto>
    studentGetBuyPagesHistory(
            @RequestParam(defaultValue = "0") Long pageNumber,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate
    ) {
        Pageable pageable = PageRequest.of(pageNumber.intValue(), pageSize.intValue());

        // Tạo DTO từ tham số request
        GetPaymentHistoryRequestDto paymentBuyPagesRequestDto = new GetPaymentHistoryRequestDto(startDate, endDate);

        return ResponseEntity.status(HttpStatus.OK)
                .body(paymentService.studentGetPaymentHistory(paymentBuyPagesRequestDto, pageable));
    }


    @GetMapping("history/spso_buy_pages")
    @PreAuthorize("hasRole('SPSO')")
    public ResponseEntity<AdminGetAllPaymentResponseDto> spsoGetBuyPagesHistory(@RequestBody GetPaymentHistoryRequestDto paymentBuyPagesRequestDto,
                                                    @RequestParam(defaultValue = "0") Long pageNumber,
                                                    @RequestParam(defaultValue = "10") Long pageSize) {
        Pageable pageable = PageRequest.of(pageNumber.intValue(), pageSize.intValue());
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.adminGetAllPaymentHistory(paymentBuyPagesRequestDto, pageable));
    }

    @GetMapping("history/admin_buy_pages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminGetAllPaymentResponseDto> adminGetBuyPagesHistory(@RequestBody GetPaymentHistoryRequestDto paymentBuyPagesRequestDto,
                                                     @RequestParam(defaultValue = "0") Long pageNumber,
                                                     @RequestParam(defaultValue = "10") Long pageSize) {
        Pageable pageable = PageRequest.of(pageNumber.intValue(), pageSize.intValue());
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.adminGetAllPaymentHistory(paymentBuyPagesRequestDto, pageable));
    }

    @GetMapping("student/num_pages")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentGetNumPagesDto> getNumPages() {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getNumPages());
    }

}
