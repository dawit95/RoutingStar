package com.curation.backend.route.domain;

import com.curation.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Entity
public class RouteStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn
    private User user;

    @ManyToOne(targetEntity = Route.class)
    @JoinColumn
    private Route route;

    public void setUser(User user) {

    }

    public void setRoute(Route route) {

    }
}
