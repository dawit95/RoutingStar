package com.curation.backend.comment.domain;

import com.curation.backend.global.domain.BaseTime;
import com.curation.backend.route.domain.Route;
import com.curation.backend.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@SQLDelete(sql = "UPDATE comment set deleted = true where id = ?")
@Where(clause = "deleted = false")
@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @JsonManagedReference
    @ManyToOne(targetEntity = Route.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    Route route;

    public void setRoute(Route route) {
        if(this.route != null)   this.route.getRouteComments().remove(this);

        this.route = route;
        this.getRoute().getRouteComments().add(this);
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private boolean deleted = Boolean.FALSE;

    @Builder
    public Comment(String comment) {
        this.comment = comment;
    }
}
