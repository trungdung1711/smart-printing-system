package com.Anonymous.smart_printing_system.repository;

import com.Anonymous.smart_printing_system.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDateTime;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.student.studentId = :studentId AND p.payDate BETWEEN :startDate AND :endDate ORDER BY p.payDate DESC")
    Page<Payment> findPaymentsByStudentIdAndDateRange(
            Long studentId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );

    @Query("SELECT p FROM Payment p WHERE p.payDate BETWEEN :startDate AND :endDate ORDER BY p.payDate DESC")
    Page<Payment> findPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

}
