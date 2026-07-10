package com.letit.letit.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private BaseProfile baseProfile;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String text;


    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment parentComment;

    private LocalDate createdDate;

}
