package com.curation.backend.route.dto;

import com.curation.backend.place.dto.PlaceRequestDto;
import com.curation.backend.route.domain.Route;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RouteRequestDto {

    private String thumbnail;
    private String routeDescription;
    private Long id;
    private List<PlaceRequestDto> places;

    public Route toEntity() {
        return Route.builder().thumbnail(thumbnail).routeDescription(routeDescription).build();
    }
}