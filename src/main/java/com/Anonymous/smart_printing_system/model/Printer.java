package com.Anonymous.smart_printing_system.model;


import com.Anonymous.smart_printing_system.model.eenum.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


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

    @Column(name = "campus_name", nullable = false)
    private String campusName;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Enumerated
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "count", nullable = false)
    private Long count;

    @Column(name = "recent_maintenance_date")
    private LocalDateTime recentMaintenanceDate;

    @Column(name = "max", nullable = false)
    private Long max;

    @Column(name = "usage_count", nullable = false)
    private Long usageCount;
}
