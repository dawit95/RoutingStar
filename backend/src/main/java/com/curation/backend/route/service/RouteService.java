package com.curation.backend.route.service;

import com.curation.backend.place.domain.Place;
import com.curation.backend.place.domain.PlaceRepository;
import com.curation.backend.place.dto.PlaceRequestDto;
import com.curation.backend.route.domain.Route;
import com.curation.backend.route.domain.RouteRepository;
import com.curation.backend.route.dto.RouteDetailResponseDto;
import com.curation.backend.route.dto.RouteListResponseDto;
import com.curation.backend.route.dto.RouteRequestDto;
import com.curation.backend.user.domain.FollowFollowing;
import com.curation.backend.user.domain.User;
import com.curation.backend.user.domain.UserRepository;
import com.curation.backend.user.dto.UserRequestDto;
import com.curation.backend.user.dto.UserResponseDto;
import com.curation.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    private final UserService userService;


    Logger logger = LoggerFactory.getLogger(RouteService.class);

    @Transactional
    public Long save(RouteRequestDto routeRequestDto, List<PlaceRequestDto> placesRequestDto) throws Exception {

        Route route = routeRequestDto.toEntity();
        User user = userRepository.findById(routeRequestDto.getId()).get();

        route.setUser(user);
        routeRepository.save(route);

        List<Place> routePlaces = placesRequestDto.stream().map(e -> e.toEntity()).collect(Collectors.toList());

        for(Place p : routePlaces) {
            p.setRoute(route);
        }
        placeRepository.saveAll(routePlaces);

        return route.getId();
    }

    public List<RouteListResponseDto> followingRouteList(Long id) {
        User user = userRepository.findById(id).get();
        List<FollowFollowing> followList = user.getFollowers();

        List<Long> list = followList.stream().map(e -> e.getFollowing().getId()).collect(Collectors.toList());
        list.add(id);

        return routeRepository.findByUserIdIn(list).stream().map(RouteListResponseDto::new).collect(Collectors.toList());
    }

    public RouteDetailResponseDto getDetail(Long id) {
        return new RouteDetailResponseDto(routeRepository.findById(id).get());
    }
}


