package com.curation.backend.place.dto;

import com.curation.backend.place.domain.Place;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlaceResponseDto {

    private Long id;
    private String title;
    private Double lat;
    private Double lng;
    private Long createdOrder;
    private String placeImg;
    private Boolean isThumbnail;

    public PlaceResponseDto(Place entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.lat = entity.getLat();
        this.lng = entity.getLng();
        this.createdOrder = entity.getCreatedOrder();
        this.placeImg = entity.getPlaceImg();
        this.isThumbnail = entity.getIsThumbnail();
    }
}
