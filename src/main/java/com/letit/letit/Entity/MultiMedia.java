package com.letit.letit.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class MultiMedia {

    @Id
  private  String id;

    private String type;

    private String hashCode;

    private String size;

    private String url;

    private LocalDate creationDate;

    private String dimension;

}
