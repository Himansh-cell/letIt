package com.letit.letit.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "likes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"base_profile_id", "target_type", "target_id"})
})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private BaseProfile baseProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type")
    private TargetType targetType;

    @Column(name = "target_id")
    private String targetId;

}
