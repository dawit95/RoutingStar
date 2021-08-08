package com.curation.backend.place.domain;

import com.curation.backend.route.domain.Route;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String title;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @Column(nullable = true)
    private Long createdOrder;

    @Column
    private String placeImg;

    @Column
    private Boolean isThumbnail = Boolean.FALSE;

    @ManyToOne(targetEntity = Route.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    public void setRoute(Route route) {
        if(this.route != null)  this.route.getRoutePlaces().remove(this);

        this.route = route;
        route.getRoutePlaces().add(this);
    }

    @Builder
    public Place(String title, Double lat, Double lang, String placeImg, Long createdOrder, Boolean isThumbnail) {
        this.title = title;
        this.lat = lat;
        this.lng = lang;
        this.placeImg = placeImg;
        this.createdOrder = createdOrder;
        this.isThumbnail = isThumbnail;
    }
}
