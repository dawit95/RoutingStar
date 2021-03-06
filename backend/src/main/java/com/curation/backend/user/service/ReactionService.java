package com.curation.backend.user.service;

import com.curation.backend.fcm.service.AlarmService;
import com.curation.backend.fcm.service.PushService;
import com.curation.backend.route.domain.Route;
import com.curation.backend.route.domain.RouteRepository;
import com.curation.backend.route.domain.RouteStorage;
import com.curation.backend.route.domain.RouteStorageRepository;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.user.domain.*;
import com.curation.backend.user.dto.FFResponseDto;
import com.curation.backend.user.dto.FollowerResponseDto;
import com.curation.backend.user.dto.FollowingResponseDto;
import com.curation.backend.user.exception.NoUserException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReactionService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final RouteStorageRepository routeStorageRepository;
    private final FollowerFollowingRepository followerFollowingRepository;

    private final AlarmService alarmService;
    private final PushService pushService;

    private Logger logger = LoggerFactory.getLogger(ReactionService.class);

    public String setLike(Long userId, Long routeId) throws NoUserException, NoRouteException {
        String message = "";
        User user = userRepository.findById(userId).orElseThrow(() -> new NoUserException("해당하는 사용자가 없습니다."));
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new NoRouteException("해당하는 루트가 없습니다."));

        Like like = likeRepository.findByUserIdAndRouteId(userId, routeId).orElse(null);

        //이미 좋아요가 눌러져있음
        if(like != null) {
            likeRepository.deleteById(like.getId());
            message = "좋아요를 취소했습니다.";
            alarmService.addAlarm(user.getId(),route.getUser().getId(),"좋아요 알림",user.getName()+"님이 "+route.getUser().getName()+"님의 피드에 "+message);
//            pushService.searchReceivedUser(user.getId(),"좋아요 알림",user.getName()+"가 "+route.getUser().getName()+"의 피드"+message);
        } else {
            like = Like.builder().route(route).user(user).build();
            message = "좋아요를 등록했습니다.";
            likeRepository.save(like);
            alarmService.addAlarm(user.getId(),route.getUser().getId(),"좋아요 알림",user.getName()+"님이 "+route.getUser().getName()+"님의 피드에 "+message);
//            pushService.searchReceivedUser(user.getId(),"좋아요 알림",user.getName()+"가 "+route.getUser().getName()+"의 피드"+message);
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
            routeStorageRepository.deleteById(routeStorage.getId());
            message = "루트 저장을 취소했습니다.";
            alarmService.addAlarm(user.getId(),route.getUser().getId(),"루트 저장 알림",user.getName()+"님이 "+route.getUser().getName()+"님의 "+message);
        } else {
            routeStorage = RouteStorage.builder().route(route).user(user).build();
            message = "루트를 저장했습니다.";
            routeStorageRepository.save(routeStorage);
            alarmService.addAlarm(user.getId(),route.getUser().getId(),"루트 저장 알림",user.getName()+"님이 "+route.getUser().getName()+"님의 "+message);
        }

        return message;
    }

    public String setFollow(Long userId, Long targetId)  throws NoUserException, NoRouteException {
        String message = "";
        User user = userRepository.findById(userId).orElseThrow(() -> new NoUserException("당신의 정보가 없습니다.\n회원가입해주세요"));
        User target = userRepository.findById(targetId).orElseThrow(() -> new NoUserException("해당하는 사용자가 없습니다."));

        FollowerFollowing followerFollowing = followerFollowingRepository.findByFollower_IdAndAndFollowing_Id(userId,targetId).orElse(null);

        //이미 저장되어 있음
        if(followerFollowing != null) {
            followerFollowingRepository.deleteById(followerFollowing.getId());
            message = "언팔로우 했습니다.";
            alarmService.addAlarm(user.getId(),target.getId(),"팔로우 알림",user.getName()+"님이 "+target.getName()+"님을 "+message);
        } else {
            followerFollowing = followerFollowing.builder().follower(user).following(target).build();
            message = "팔로우 했습니다.";
            followerFollowingRepository.save(followerFollowing);
            alarmService.addAlarm(user.getId(),target.getId(),"팔로우 알림",user.getName()+"님이 "+target.getName()+"님을 "+message);
        }

        return message;
    }

    public FFResponseDto countOfFollow(Long userId) throws NoUserException, NoRouteException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoUserException("해당하는 사용자가 없습니다."));

        //userID가 follow한 것 == 유저의 following  list.
        List<FollowingResponseDto> followingList = followerFollowingRepository.findAllByFollower(user).stream().map(FollowingResponseDto::new).collect(Collectors.toList());;
        List<FollowerResponseDto> followerList = followerFollowingRepository.findAllByFollowing(user).stream().map(FollowerResponseDto::new).collect(Collectors.toList());;

        FFResponseDto ffResponseDto = new FFResponseDto(followerList,followingList);
        return ffResponseDto;
    }

    public List<Long> likeList(Long userId) {
        List<Long> list = likeRepository.findAllByUserId(userId).stream().map(e -> e.getRoute().getId()).collect(Collectors.toList());;
        return list;
    }

    public List<Long> storeList(Long userId) {
        List<Long> list = routeStorageRepository.findAllByUserId(userId).stream().map(e -> e.getRoute().getId()).collect(Collectors.toList());
        return list;
    }

}
