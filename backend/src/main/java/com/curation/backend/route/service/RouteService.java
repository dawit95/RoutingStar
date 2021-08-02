package com.curation.backend.route.service;

import com.curation.backend.place.domain.Place;
import com.curation.backend.place.domain.PlaceRepository;
import com.curation.backend.place.dto.PlaceRequestDto;
import com.curation.backend.route.domain.Route;
import com.curation.backend.route.domain.RouteRepository;
import com.curation.backend.route.dto.RouteDetailResponseDto;
import com.curation.backend.route.dto.RouteListResponseDto;
import com.curation.backend.route.dto.RouteRequestDto;
import com.curation.backend.tag.domain.*;
import com.curation.backend.tag.service.TagService;
import com.curation.backend.user.domain.*;
import com.curation.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    private final TagService tagService;


    Logger logger = LoggerFactory.getLogger(RouteService.class);

    @Transactional
    public Long save(RouteRequestDto routeRequestDto, List<PlaceRequestDto> placesRequestDto, List<Long> whatTagIds, List<Long> withTagIds) throws Exception {

        Route route = routeRequestDto.toEntity();
        User user = userRepository.findById(routeRequestDto.getId()).get();

        route.setUser(user);
        routeRepository.save(route);

        List<Place> routePlaces = placesRequestDto.stream().map(e -> e.toEntity()).collect(Collectors.toList());

        for(Place p : routePlaces) {
            p.setRoute(route);
        }
        placeRepository.saveAll(routePlaces);

        tagService.addWhatTag(whatTagIds, route);
        tagService.addWithTag(withTagIds, route);

        return route.getId();
    }

    @Transactional(readOnly = true)
    public List<RouteListResponseDto> followingRouteList(Long id) {
        User user = userRepository.findById(id).get();
        List<FollowFollowing> followList = user.getFollowers();

        List<Long> list = followList.stream().map(e -> e.getFollowing().getId()).collect(Collectors.toList());
        list.add(id);

        return routeRepository.findByUserIdInOrderByCreatedAtDesc(list).stream().map(RouteListResponseDto::new).collect(Collectors.toList());
    }

    public RouteDetailResponseDto getDetail(Long id) {
        return new RouteDetailResponseDto(routeRepository.findById(id).get());
    }

    public List<RouteListResponseDto> likeRouteList() {
        return routeRepository.findAllOrderByLikeCount().stream().map(RouteListResponseDto::new).collect(Collectors.toList());
    }
}


