package com.curation.backend.comment.domain;

import com.curation.backend.global.domain.BaseTime;
import com.curation.backend.route.domain.Route;
import com.curation.backend.user.domain.User;
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
    private String content;

    @Column(nullable = false)
    private Long level;

    @Column(nullable = false)
    private Long commentOrder;

    @Column(nullable = false)
    private Long groupNumber;

    @ManyToOne(targetEntity = Route.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    Route route;

    public void setRoute(Route route) {
        if(route != null)   this.route.getRouteComments().remove(this);

        this.route = route;
        this.getRoute().getRouteComments().add(this);
    }

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public void setUser(User user) {

    }

    @Column
    private boolean deleted = Boolean.FALSE;
}
