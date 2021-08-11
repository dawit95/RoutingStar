package com.curation.backend.place.dto;

import com.curation.backend.place.domain.Place;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlaceResponseDto {

    private Long id;
    private String title;
    private String lat;
    private String lang;
    private Long placeOrder;
    private String placeImg;

    public PlaceResponseDto(Place entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.lat = entity.getLat();
        this.lang = entity.getLang();
        this.placeOrder = entity.getPlaceOrder();
        this.placeImg = entity.getPlaceImg();
    }
}
