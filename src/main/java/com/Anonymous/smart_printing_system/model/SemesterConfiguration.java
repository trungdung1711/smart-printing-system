package com.Anonymous.smart_printing_system.model;


import com.Anonymous.smart_printing_system.model.eenum.FileType;
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
@Table(name = "configuration")
public class SemesterConfiguration
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "default_page_number", nullable = false)
    private Long defaultPageNumber;

    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    @ElementCollection
    @CollectionTable(name = "accepted_file_type", joinColumns = @JoinColumn(name = "id"))
    private Set<FileType> acceptedFileType = new LinkedHashSet<>();

    @Column(name = "semester_name", nullable = false, unique = true)
    private String semesterName;


}
