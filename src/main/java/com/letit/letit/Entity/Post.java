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

    private String caption;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private Long likes;

    private Long comments;

    private java.time.LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<MultiMedia> media;



}
