package com.Anonymous.smart_printing_system.repository;

import com.Anonymous.smart_printing_system.model.PageConfig;
import com.Anonymous.smart_printing_system.model.Payment;
import com.Anonymous.smart_printing_system.model.eenum.PageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.student.studentId = :studentId AND p.payDate BETWEEN :startDate AND :endDate")
    Page<Payment> findPaymentsByStudentIdAndDateRange(
            Long studentId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
}
