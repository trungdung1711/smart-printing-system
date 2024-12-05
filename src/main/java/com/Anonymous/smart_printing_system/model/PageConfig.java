package com.Anonymous.smart_printing_system.model;

import com.Anonymous.smart_printing_system.model.eenum.PageType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "page_config")
public class PageConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated
    @Column(name = "pageType", nullable = false, unique = true)
    private PageType pageType;

    @Column(name = "price", nullable = false)
    private Long price;

}
