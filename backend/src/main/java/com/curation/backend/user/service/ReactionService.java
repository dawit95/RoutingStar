package com.curation.backend.user.service;

import com.curation.backend.route.domain.Route;
import com.curation.backend.route.domain.RouteRepository;
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

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReactionService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
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
}
