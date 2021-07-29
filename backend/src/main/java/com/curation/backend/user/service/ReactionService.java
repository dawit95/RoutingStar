package com.curation.backend.user.service;

import com.curation.backend.route.domain.Route;
import com.curation.backend.route.domain.RouteRepository;
import com.curation.backend.route.domain.RouteStorage;
import com.curation.backend.route.domain.RouteStorageRepository;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.user.domain.Like;
import com.curation.backend.user.domain.LikeRepository;
import com.curation.backend.user.domain.User;
import com.curation.backend.user.domain.UserRepository;
import com.curation.backend.user.exception.NoUserException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReactionService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final RouteStorageRepository routeStorageRepository;

    private Logger logger = LoggerFactory.getLogger(ReactionService.class);

    public String setLike(Long userId, Long routeId) throws NoUserException, NoRouteException {
        String message = "";
        User user = userRepository.findById(userId).orElseThrow(() -> new NoUserException("해당하는 사용자가 없습니다."));
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new NoRouteException("해당하는 루트가 없습니다."));

        Like like = likeRepository.findByUserIdAndRouteId(userId, routeId).orElse(null);

        //이미 좋아요가 눌러져있음
        if(like != null) {
            likeRepository.deleteById(like.getId());
            message = "좋아요 취소";
        } else {
            like = Like.builder().route(route).user(user).build();
            message = "좋아요 등록";
            likeRepository.save(like);
        }

        return message;
    }

    public String setStore(Long userId, Long routeId) throws NoUserException, NoRouteException {
        String message = "";
        User user = userRepository.findById(userId).orElseThrow(() -> new NoUserException("해당하는 사용자가 없습니다."));
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new NoRouteException("해당하는 루트가 없습니다."));

        RouteStorage routeStorage = routeStorageRepository.findByUserIdAndRouteId(userId, routeId).orElse(null);

        //이미 저장되어 있음
        if(routeStorage != null) {
            likeRepository.deleteById(routeStorage.getId());
            message = "루트 저장 취소";
        } else {
            routeStorage = RouteStorage.builder().route(route).user(user).build();
            message = "루트 저장 성공";
            routeStorageRepository.save(routeStorage);
        }

        return message;
    }
}
