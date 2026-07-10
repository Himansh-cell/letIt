package com.letit.letit.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private BaseProfile baseProfile;

    private String target_type;

    private  String target_id;

}
