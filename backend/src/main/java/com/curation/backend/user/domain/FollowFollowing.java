package com.curation.backend.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"}))
@NoArgsConstructor
@Getter
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

/*
    public void setUser(User user) {
        if(this.user != null)   this.user.getRoutes().remove(this);

        this.user = user;
        user.getRoutes().add(this);
    }
 */


    @Builder
    public FollowFollowing(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}
