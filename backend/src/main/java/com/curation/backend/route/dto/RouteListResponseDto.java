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

@Getter @Setter
public class RouteListResponseDto {

    private Long id;
    private String routeImg;
    private String routeDescription;
    private LocalDateTime modifiedAt;
    private UserResponseDto user;
    private List<PlaceResponseDto> places;
    private List<WhatTagResponseDto> whatTag;
    private List<WithTagResponseDto> withTag;

    private Long likeCnt;
    private Long storageCnt;
    private Long commentCnt;
    private Boolean isLiked = false;
    private Boolean isStored = false;

    public RouteListResponseDto(Route entity) {
        this.id = entity.getId();
        this.routeImg = entity.getRouteImg();
        this.routeDescription = entity.getRouteDescription();
        this.modifiedAt = entity.getModifiedAt();
        this.user = new UserResponseDto(entity.getUser());
        this.places = entity.getRoutePlaces().stream().map(PlaceResponseDto::new).collect(Collectors.toList());

        this.whatTag = entity.getRouteWhatTags().stream().map(e -> new WhatTagResponseDto(e.getWhatTag())).collect(Collectors.toList());
        this.withTag = entity.getRouteWithTags().stream().map(e -> new WithTagResponseDto(e.getWithTag())).collect(Collectors.toList());

        this.likeCnt = Long.valueOf(entity.getRouteLikes().size());
        this.storageCnt = Long.valueOf(entity.getRouteStorages().size());
        this.commentCnt = Long.valueOf(entity.getRouteComments().size());
    }
}
