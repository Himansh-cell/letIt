package com.letit.letit.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private BaseProfile baseProfile;

    @Column( nullable = false)
   private String name;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false)
   private Gender gender;

    @Column( nullable = false)
   private Date dob;

    @Column( nullable = false)
   private String address;

    @Column( nullable = false)
   private String phone;

    @Column( nullable = true)
   private String photo;

}
