package com.Anonymous.smart_printing_system.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("SPSO")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spso extends SystemUser {
    @OneToMany(mappedBy = "spso",
            orphanRemoval = true,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    private Set<Printer> printers = new LinkedHashSet<>();
}
