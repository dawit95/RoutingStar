package com.curation.backend.user.domain;

import com.curation.backend.route.domain.Route;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "route_id"}))
@Getter
@NoArgsConstructor
@Entity
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Route.class)
    @JoinColumn(name = "route_id")
    private Route route;

    @Builder
    public Like(User user, Route route) {
        this.user = user;
        this.route = route;
    }

}
