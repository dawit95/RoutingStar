package com.curation.backend.tag.domain;

import com.curation.backend.route.domain.Route;

import javax.persistence.*;

@Entity
public class RouteWithTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public void setRoute(Route route) {
        if(this.route != null)  this.route.getRouteWithTags().remove(this);

        this.route = route;
        this.route.getRouteWithTags().add(this);
    }

    @ManyToOne
    @JoinColumn(name = "whatTag_id")
    private WithTag withTag;

    public void setWithTag(WithTag withTag) {
        if(this.withTag != null)    this.withTag.getRouteWithTags().remove(this);

        this.withTag = withTag;
        this.withTag.getRouteWithTags().add(this);
    }

}
