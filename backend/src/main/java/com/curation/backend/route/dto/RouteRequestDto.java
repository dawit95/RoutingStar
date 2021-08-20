package com.curation.backend.route.dto;

import com.curation.backend.place.dto.PlaceRequestDto;
import com.curation.backend.route.domain.Route;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RouteRequestDto {

    private Long id;
    private String routeImg;
    private String routeDescription;
    private List<PlaceRequestDto> places;
    private List<Long> whatTag;
    private List<Long> withTag;

    public Route toEntity() {
        return Route.builder().thumbnail(routeImg).routeDescription(routeDescription).build();
    }
}