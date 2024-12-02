package com.Anonymous.smart_printing_system.model;

import com.Anonymous.smart_printing_system.model.eenum.SexEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class SystemUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;


    @Column(name = "email",
            length = 255,
            nullable = false,
            unique = true)
    private String email;


    @Column(name = "password",
            nullable = false,
            length = 1000)
    private String password;


    @Enumerated
    @Column(name = "sex")
    private SexEnum sex;


    @Column(name = "last_name",
            nullable = false)
    private String lastName;


    @Column(name = "first_name",
            nullable = false)
    private String firstName;


    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();
}