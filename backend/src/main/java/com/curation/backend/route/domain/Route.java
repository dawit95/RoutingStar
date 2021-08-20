package com.curation.backend.route.domain;

import com.curation.backend.comment.domain.Comment;
import com.curation.backend.global.domain.BaseTime;
import com.curation.backend.place.domain.Place;
import com.curation.backend.route.dto.RouteRequestDto;
import com.curation.backend.tag.domain.RouteWhatTag;
import com.curation.backend.tag.domain.RouteWithTag;
import com.curation.backend.user.domain.Like;
import com.curation.backend.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@SQLDelete(sql = "UPDATE route set deleted = true where id = ?")
@Where(clause = "deleted = false")
@Getter
@NoArgsConstructor
@Entity
public class Route extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String routeImg;

    @Column(nullable = false)
    private String routeDescription;

    @JsonManagedReference
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "route")
    private List<Comment> routeComments = new ArrayList<>();

    @OneToMany(mappedBy = "route")
    private List<Place> routePlaces = new ArrayList<>();

    public void setUser(User user) {
        if(this.user != null)   this.user.getRoutes().remove(this);

        this.user = user;
        user.getRoutes().add(this);
    }

    @OneToMany(mappedBy = "route")
    private List<RouteWhatTag> routeWhatTags = new ArrayList<>();

    @OneToMany(mappedBy = "route")
    private List<RouteWithTag> routeWithTags = new ArrayList<>();

    @OneToMany(mappedBy = "route")
    private List<Like> routeLikes = new ArrayList<>();

    @OneToMany(mappedBy = "route")
    private List<RouteStorage> routeStorages = new ArrayList<>();

    @Column
    private boolean deleted = Boolean.FALSE;

    @Builder
    public Route(String thumbnail, String routeDescription) {
        this.routeImg = thumbnail;
        this.routeDescription = routeDescription;
    }

    public void delete() {
        this.deleted = Boolean.TRUE;
    }

    public void modify(RouteRequestDto route) {
        this.routeImg = route.getRouteImg();
        this.routeDescription = route.getRouteDescription();
    }
}
