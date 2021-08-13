package com.curation.backend.route.dto;

import com.curation.backend.comment.dto.CommentResponseDto;
import com.curation.backend.place.dto.PlaceResponseDto;
import com.curation.backend.route.domain.Route;
import com.curation.backend.tag.dto.WhatTagResponseDto;
import com.curation.backend.tag.dto.WithTagResponseDto;
import com.curation.backend.user.dto.UserResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class RouteDetailWithCommentResponseDto {
    private Long id;
    private String routeImg;
    private String routeDescription;
    private LocalDateTime modifiedAt;
    private List<PlaceResponseDto> places;
    private UserResponseDto user;
    private List<WhatTagResponseDto> whatTag;
    private List<WithTagResponseDto> withTag;
    private List<CommentResponseDto> comments;
    private Long likeCnt = Long.valueOf(0);
    private Long storageCnt = Long.valueOf(0);
    private Long commentCnt = Long.valueOf(0);
    private Boolean isLiked = false;
    private Boolean isStored = false;

    public RouteDetailWithCommentResponseDto(Route entity) {
        this.id = entity.getId();
        this.routeImg = entity.getRouteImg();
        this.routeDescription = entity.getRouteDescription();
        this.modifiedAt = entity.getModifiedAt();
        this.places = entity.getRoutePlaces().stream().map(PlaceResponseDto::new).collect(Collectors.toList());
        this.user = new UserResponseDto(entity.getUser());
        this.whatTag = entity.getRouteWhatTags().stream().map(e -> new WhatTagResponseDto(e.getWhatTag())).collect(Collectors.toList());
        this.withTag = entity.getRouteWithTags().stream().map(e -> new WithTagResponseDto(e.getWithTag())).collect(Collectors.toList());
        this.comments = entity.getRouteComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    public void setOptions(Long likeCnt, Long storageCnt, Long commentCnt, Boolean isLiked, Boolean isStored) {
        this.likeCnt = likeCnt;
        this.storageCnt = storageCnt;
        this.commentCnt = commentCnt;
        this.isLiked = isLiked;
        this.isStored = isStored;
    }

    public void setCount(Long likeCnt, Long storageCnt, Long commentCnt) {
        this.likeCnt = likeCnt;
        this.storageCnt = storageCnt;
        this.commentCnt = commentCnt;
    }

}
