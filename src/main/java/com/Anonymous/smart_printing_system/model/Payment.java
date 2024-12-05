package com.Anonymous.smart_printing_system.model;

import com.Anonymous.smart_printing_system.model.eenum.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id", nullable = false)
    private Long payId;

    @Column(name = "pay_cost", nullable = false)
    private Double payCost;

    @Column(name = "pay_date", nullable = false)
    private LocalDateTime payDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "number_of_pages", nullable = false)
    private Long numberOfPages;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

}
