package com.Anonymous.smart_printing_system.model;

import com.Anonymous.smart_printing_system.model.eenum.PageOrientation;
import com.Anonymous.smart_printing_system.model.eenum.PrintingLogEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "printing_log")
public class PrintingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "log_status", nullable = false)
    private PrintingLogEnum logStatus;

    @Column(name = "log_desc")
    private String logDescription;

    /*
    Time to get the document
     */
    @Column(name = "log_date", nullable = false)
    private LocalDateTime logDate;

    @Column(name = "log_start_time")
    private LocalDateTime logStartTime;

    @Column(name = "log_end_time")
    private LocalDateTime logEndTime;

    @Column(name = "page_per_sheet", nullable = false)
    private Long pagePerSheet;

    @Column(name = "number_of_copy", nullable = false)
    private Long numberOfCopy;

    @Enumerated
    @Column(name = "page_orientation", nullable = false)
    private PageOrientation pageOrientation;

    @OneToOne(mappedBy = "printingLog", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, optional = false, orphanRemoval = true)
    private Document document;

    @ManyToOne(optional = false)
    @JoinColumn(name = "printer_id", nullable = false)
    private Printer printer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}