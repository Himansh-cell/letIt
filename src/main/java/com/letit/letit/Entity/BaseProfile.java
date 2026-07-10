package com.letit.letit.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "base_profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(unique = true)
    private String userName;

    @Column( nullable = false)
     private String password;

    @Column(unique = true, nullable = false)
     private String email;

    private boolean isActive;

    private boolean statusPublic;

    @Column(nullable = false)
    private LocalDate createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;



}
