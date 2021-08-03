package com.curation.backend.place.dto;

import com.curation.backend.place.domain.Place;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlaceRequestDto {

    private String title;
    private String lat;
    private String lng;
    private Long createdOrder;
    private String placeImg;
    private Boolean isThumbnail = false;

    public Place toEntity() {
        return Place.builder().title(title).lat(lat).lang(lng).placeImg(placeImg).createdOrder(createdOrder).isThumbnail(isThumbnail).build();
    }
}
