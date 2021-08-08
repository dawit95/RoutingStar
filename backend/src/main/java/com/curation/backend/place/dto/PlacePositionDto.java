package com.curation.backend.place.dto;

import com.curation.backend.place.domain.Place;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlacePositionDto {
    private Double lat;
    private Double lng;

    public PlacePositionDto(Place entity) {
        this.lat = entity.getLat();
        this.lng = entity.getLng();
    }
}
