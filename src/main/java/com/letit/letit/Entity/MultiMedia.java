package com.letit.letit.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class MultiMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String id;

    private String type;

    private String hashCode;

    private String criteria;

    private String size;

    private String url;

    private LocalDate creationDate;

    private String dimension;

    private String mongoFileId;

    @ManyToOne
    @jakarta.persistence.JoinColumn(name = "post_id")
    private Post post;


}
