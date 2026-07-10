package com.letit.letit.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne
    private BaseProfile baseProfile;

    private Long likes;

    private Long comments;






}
