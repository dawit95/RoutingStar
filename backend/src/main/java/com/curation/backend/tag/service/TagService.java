package com.curation.backend.tag.service;

import com.curation.backend.route.domain.Route;
import com.curation.backend.tag.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {

    private final WhatTagRepository whatTagRepository;
    private final WithTagRepository withTagRepository;
    private final RouteWhatTagRepository routeWhatTagRepository;
    private final RouteWithTagRepository routeWithTagRepository;

    public void addWhatTag(List<Long> whatTagIds, Route route) {

        List<WhatTag> whatTags = whatTagRepository.findByIdIn(whatTagIds);
        List<RouteWhatTag> routeWhatTags = new ArrayList<>();
        for(WhatTag what : whatTags) {
            RouteWhatTag routeWhatTag = RouteWhatTag.builder().whatTag(what).route(route).build();
            routeWhatTag.setWhatTag(what);
            routeWhatTagRepository.save(routeWhatTag);
        }
    }

    public void addWithTag(List<Long> withTagIds, Route route) {
        List<WithTag> withTags = withTagRepository.findByIdIn(withTagIds);

        List<RouteWithTag> routeWithTags = new ArrayList<>();
        for(WithTag with : withTags) {
            RouteWithTag routeWithTag = RouteWithTag.builder().withTag(with).route(route).build();
            routeWithTag.setWithTag(with);
            routeWithTagRepository.save(routeWithTag);
        }
    }

    public void deleteWhatTag(Long id) {
        routeWhatTagRepository.deleteAllByRouteId(id);
    }

    public void deleteWithTag(Long id) {
        routeWithTagRepository.deleteAllByRouteId(id);
    }

}
