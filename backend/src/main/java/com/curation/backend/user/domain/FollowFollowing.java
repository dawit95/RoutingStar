package com.curation.backend.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Entity
public class FollowFollowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "following_id")
    private User following;

    public void setFollower(User follower) {

    }

    public void setFollowing(User following) {

    }
}
