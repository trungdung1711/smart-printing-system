package com.Anonymous.smart_printing_system.model;


import com.Anonymous.smart_printing_system.model.eenum.FileType;
import com.Anonymous.smart_printing_system.model.eenum.PageType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "document")
public class Document
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated
    @Column(name = "page_type", nullable = false)
    private PageType pageType;

    @Column(name = "size", nullable = false)
    private Long size;

    @Enumerated
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "page_number", nullable = false)
    private Long pageNumber;

    @OneToOne(optional = false)
    @JoinColumn(name = "printing_log_id", nullable = false)
    private PrintingLog printingLog;

    @Column(name = "start", nullable = false)
    private Long start;

    @Column(name = "end", nullable = false)
    private Long end;
}
