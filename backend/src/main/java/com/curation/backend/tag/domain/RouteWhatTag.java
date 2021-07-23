package com.curation.backend.tag.domain;

import com.curation.backend.route.domain.Route;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RouteWhatTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public void setRoute(Route route) {
        if(this.route != null)  this.route.getRouteWhatTags().remove(this);

        this.route = route;
        this.route.getRouteWhatTags().add(this);
    }

    @ManyToOne
    @JoinColumn(name = "whatTag_id")
    private WhatTag whatTag;

    public void setWhatTag(WhatTag whatTag) {
        if(this.whatTag != null)    this.whatTag.getRouteWhatTags().remove(this);

        this.whatTag = whatTag;
        this.whatTag.getRouteWhatTags().add(this);
    }

}
