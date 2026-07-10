package com.letit.letit.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "follows")
@Getter
@Setter
@NoArgsConstructor
public class Follow {

    @EmbeddedId
    private FollowId id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private BaseProfile follower;

    @ManyToOne
    @MapsId("followingId")
    @JoinColumn(name = "following_id")
    private BaseProfile following;

    private String status;
}