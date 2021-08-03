package com.curation.backend.route.service;

import com.curation.backend.place.domain.Place;
import com.curation.backend.place.domain.PlaceRepository;
import com.curation.backend.place.dto.PlaceRequestDto;
import com.curation.backend.route.domain.Route;
import com.curation.backend.route.domain.RouteRepository;
import com.curation.backend.route.dto.RouteDetailResponseDto;
import com.curation.backend.route.dto.RouteListResponseDto;
import com.curation.backend.route.dto.RouteRequestDto;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.tag.service.TagService;
import com.curation.backend.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        List<FollowerFollowing> followList = user.getFollowers();

        List<Long> list = followList.stream().map(e -> e.getFollowing().getId()).collect(Collectors.toList());
        list.add(id);

        return routeRepository.findByUserIdInOrderByCreatedAtDesc(list).stream().map(RouteListResponseDto::new).collect(Collectors.toList());
    }

    public RouteDetailResponseDto getDetail(Long id) throws NoRouteException {
        Optional<Route> route = Optional.ofNullable(routeRepository.findById(id).orElseThrow(() -> new NoRouteException("해당하는 루트가 없습니다.")));
        return new RouteDetailResponseDto(route.get());
    }

    public List<RouteListResponseDto> likeRouteList() {
        return routeRepository.findAllOrderByLikeCount().stream().map(RouteListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long modifyRoute(Long id, RouteRequestDto routeRequestDto, List<PlaceRequestDto> placesRequestDto, List<Long> whatTagIds, List<Long> withTagIds) throws NoRouteException {
        Optional<Route> route = Optional.ofNullable(routeRepository.findById(id).orElseThrow(() -> new NoRouteException("해당하는 루트가 없습니다.")));

        route.get().modify(routeRequestDto);

        //기존에 연결되어 있던 것들 모두 삭제 필요
        placeRepository.deleteAllByRouteId(id);
        tagService.deleteWhatTag(id);
        tagService.deleteWithTag(id);

        List<Place> routePlaces = placesRequestDto.stream().map(e -> e.toEntity()).collect(Collectors.toList());

        for(Place p : routePlaces) {
            p.setRoute(route.get());
        }

        placeRepository.saveAll(routePlaces);

        tagService.addWhatTag(whatTagIds, route.get());
        tagService.addWithTag(withTagIds, route.get());

        return id;
    }

    public void deleteRoute(Long id) throws NoRouteException {
        Optional<Route> route = Optional.ofNullable(routeRepository.findById(id).orElseThrow(() -> new NoRouteException("해당하는 루트가 없습니다.")));
        route.get().delete();
        routeRepository.save(route.get());
    }
}


