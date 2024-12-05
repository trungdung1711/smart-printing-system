package com.Anonymous.smart_printing_system.model;

import com.Anonymous.smart_printing_system.model.eenum.ReportType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "report_revenue", nullable = false)
    private Long reportRevenue;

    @Column(name = "report_print_count", nullable = false)
    private Long reportPrintCount;

    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;

    @Enumerated
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @Column(name = "url", nullable = false)
    private String url;
}