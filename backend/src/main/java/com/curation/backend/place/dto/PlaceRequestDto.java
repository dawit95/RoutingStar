package com.curation.backend.place.dto;

import com.curation.backend.place.domain.Place;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlaceRequestDto {

    private String title;
    private String lat;
    private String lang;
    private Long placeOrder;
    private String placeImg;

    public Place toEntity() {
        return Place.builder().title(title).lat(lat).lang(lang).placeImg(placeImg).placeOrder(placeOrder).build();
    }
}
