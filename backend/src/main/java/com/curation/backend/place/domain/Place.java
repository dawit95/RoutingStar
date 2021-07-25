package com.curation.backend.place.domain;

import com.curation.backend.route.domain.Route;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String title;

    @Column(nullable = false)
    private String lat;

    @Column(nullable = false)
    private String lang;

    @Column(nullable = true)
    private Long placeOrder;

    @Column
    private String placeImg;

    @ManyToOne(targetEntity = Route.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    public void setRoute(Route route) {
        if(this.route != null)  this.route.getRoutePlaces().remove(this);

        this.route = route;
        route.getRoutePlaces().add(this);
    }

    @Builder
    public Place(String title, String lat, String lang, String placeImg, Long placeOrder) {
        this.title = title;
        this.lat = lat;
        this.lang = lang;
        this.placeImg = placeImg;
        this.placeOrder = placeOrder;
    }
}
