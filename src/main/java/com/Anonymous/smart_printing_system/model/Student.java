package com.Anonymous.smart_printing_system.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("STUDENT")
public class Student extends SystemUser 
{
    @Column(name = "student_num_remained", nullable = false)
    private Long studentNumRemained;

    @Column(name = "student_id", nullable = false, unique = true)
    private Long studentId;

    @Column(name = "used_page", nullable = false)
    private Long usedPage;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<PrintingLog> printingLogs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Order> orders = new LinkedHashSet<>();

}
