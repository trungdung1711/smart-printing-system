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
    
    @Column(name = "num_Of_Pages", nullable = false, unique = true)
    private Long numberOfPages;

    @Column(name = "price", nullable = false)
    private Long price;

}
