package com.curation.backend.place.domain;

import com.curation.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class PlaceImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String placeImg;

    @ManyToOne(targetEntity = Place.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    public void setPlace(Place place) {
        if(this.place != null)  this.place.getRoutePlaceImg().remove(this);

        this.place = place;
        place.getRoutePlaceImg().add(this);
    }

}
