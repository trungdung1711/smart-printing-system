package com.Anonymous.smart_printing_system.model;


import com.Anonymous.smart_printing_system.model.eenum.PrinterStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "printer")
public class Printer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "model", nullable = false, unique = true)
    private String model;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "building_name", nullable = false)
    private String buildingName;
    // H1, H2, H3, H6
    // A1, A2, B3, B6

    @Column(name = "campus_name", nullable = false)
    private String campusName;
    // LTK, DA

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Enumerated
    @Column(name = "status", nullable = false)
    private PrinterStatus printerStatus = PrinterStatus.ON;

    @Column(name = "recent_maintenance_date")
    private LocalDateTime recentMaintenanceDate;

    @Column(name = "max", nullable = false)
    private Long max = 1_000_000L;

    @Column(name = "usage_count", nullable = false)
    private Long usageCount = 0L;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "spso_id", nullable = false)
    private Spso spso;

    @OneToMany(mappedBy = "printer", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<PrintingLog> printingLogs = new LinkedHashSet<>();
}
