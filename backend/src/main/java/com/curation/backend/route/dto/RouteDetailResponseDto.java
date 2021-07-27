package com.curation.backend.route.dto;

import com.curation.backend.place.dto.PlaceResponseDto;
import com.curation.backend.route.domain.Route;
import com.curation.backend.tag.dto.WhatTagResponseDto;
import com.curation.backend.tag.dto.WithTagResponseDto;
import com.curation.backend.user.dto.UserResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
route 게시물의 상세 페이지에서 연관된 place와 함께 조회된다.
 */
@Getter @Setter
public class RouteDetailResponseDto {

    private Long id;
    private String thumbnail;
    private String routeDescription;
    private LocalDateTime modifiedAt;
    private List<PlaceResponseDto> places;
    private UserResponseDto user;
    private List<WhatTagResponseDto> whatTags;
    private List<WithTagResponseDto> withTags;



    public RouteDetailResponseDto(Route entity) {
        this.id = entity.getId();
        this.thumbnail = entity.getThumbNail();
        this.routeDescription = entity.getRouteDescription();
        this.modifiedAt = entity.getModifiedAt();
        this.places = entity.getRoutePlaces().stream().map(PlaceResponseDto::new).collect(Collectors.toList());
        this.user = new UserResponseDto(entity.getUser());
        this.whatTags = entity.getRouteWhatTags().stream().map(e -> new WhatTagResponseDto(e.getWhatTag())).collect(Collectors.toList());
        this.withTags = entity.getRouteWithTags().stream().map(e -> new WithTagResponseDto(e.getWithTag())).collect(Collectors.toList());
    }

}
