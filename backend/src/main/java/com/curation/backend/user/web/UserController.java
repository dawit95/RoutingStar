package com.curation.backend.user.web;

import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.route.domain.Route;
import com.curation.backend.route.domain.RouteRepository;
import com.curation.backend.route.dto.RouteDetailResponseDto;
import com.curation.backend.route.dto.RouteListResponseDto;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.route.service.RouteService;
import com.curation.backend.tag.domain.WhatTag;
import com.curation.backend.tag.domain.WhatTagRepository;
import com.curation.backend.tag.domain.WithTag;
import com.curation.backend.tag.domain.WithTagRepository;
import com.curation.backend.token.service.TokenService;
import com.curation.backend.user.domain.*;
import com.curation.backend.user.dto.UserRequestDto;
import com.curation.backend.user.dto.UserResponseDto;
import com.curation.backend.user.exception.NoUserException;
import com.curation.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ResponseGenerateService responseGenerateService;

    private final WhatTagRepository whatTagRepository;
    private final WithTagRepository withTagRepository;
    private final FollowerFollowingRepository followerFollowingRepository;
    private final LikeRepository likeRepository;
    private final RouteRepository routeRepository;

    private final TokenService tokenService;
    private final RouteService routeService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/Info/{targetId}")
    public ResponseEntity<SuccessResponseDto> getUserInfo(@PathVariable Long targetId, ServletRequest request) throws NoUserException, NoRouteException {

        UserResponseDto userResponseDto =  userService.getUserInfo(targetId);
        logger.trace("유저는 찾았다. {} ",userResponseDto);
        String token = ((HttpServletRequest)request).getHeader("access_token");
        logger.trace("access_token도 잘왔다. 찾았다. {} ",token);
        if (token != null) {
            Long userId = tokenService.getId(token);

            FollowerFollowing followerFollowing = followerFollowingRepository.findByFollower_IdAndAndFollowing_Id(targetId,userId).orElse(null);
            logger.trace("{}가 {}의 follow를 눌렀다. {} ",userId,targetId,userResponseDto);
            //follow정보 저장
            userResponseDto.setFollowed(followerFollowing != null?true:false);
        }

        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(userResponseDto);

        return new ResponseEntity<SuccessResponseDto>(successResponseDto, HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<SuccessResponseDto> setUserDetail(@RequestBody UserRequestDto userRequestDto) throws NoUserException, NoRouteException {
        String message = userService.updateUserInfo(userRequestDto);
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(message);

        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @GetMapping("/mypage/routes/{userId}")
    public ResponseEntity<SuccessResponseDto> myRouteList(@PathVariable("userId") Long id) {
        List<RouteListResponseDto> list = routeService.myRouteList(id);
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(list);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @GetMapping("/mypage/stores/{userId}")
    public ResponseEntity<SuccessResponseDto> myStoreList(@PathVariable("userId") Long id) {
        List<RouteDetailResponseDto> list = routeService.myStoreList(id);
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(list);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @GetMapping("")
    public void test() {

        List<User> users = new ArrayList<>();
        User user1 = User.builder().name("사용자1").role(Role.USER).email("user1@naver.com").userDescription(null).fileName(null).profileImg(null).build();
        User user2 = User.builder().name("사용자2").role(Role.USER).email("user2@gmail.com").userDescription(null).fileName(null).profileImg(null).build();
        User user3 = User.builder().name("사용자3").role(Role.USER).email("user3@ssafy.com").userDescription(null).fileName(null).profileImg(null).build();
        User user4 = User.builder().name("사용자4").role(Role.USER).email("user4@daum.com").userDescription(null).fileName(null).profileImg(null).build();

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        userRepository.saveAll(users);

        List<WhatTag> whatTags = new ArrayList<>();
        whatTags.add(WhatTag.builder().title("유명 관광지는 필수").build());
        whatTags.add(WhatTag.builder().title("SNS 핫플레스").build());
        whatTags.add(WhatTag.builder().title("여유롭게 힐링").build());
        whatTags.add(WhatTag.builder().title("체험/액티비티").build());
        whatTags.add(WhatTag.builder().title("문화/예술/역사").build());
        whatTags.add(WhatTag.builder().title("자연과 함께").build());
        whatTags.add(WhatTag.builder().title("관광보다 먹방").build());

        whatTagRepository.saveAll(whatTags);

        List<WithTag> withTags = new ArrayList<>();
        withTags.add(WithTag.builder().title("혼자").build());
        withTags.add(WithTag.builder().title("친구와").build());
        withTags.add(WithTag.builder().title("연인과").build());
        withTags.add(WithTag.builder().title("배우자와").build());
        withTags.add(WithTag.builder().title("아이와").build());
        withTags.add(WithTag.builder().title("부모님과").build());
        withTags.add(WithTag.builder().title("기타").build());

        withTagRepository.saveAll(withTags);

        //팔로우-팔로잉 설정
        List<FollowerFollowing> followerFollowings = new ArrayList<>();
        followerFollowings.add(FollowerFollowing.builder().follower(user1).following(user3).build());
        followerFollowings.add(FollowerFollowing.builder().follower(user1).following(user4).build());
        followerFollowings.add(FollowerFollowing.builder().follower(user2).following(user4).build());
        followerFollowings.add(FollowerFollowing.builder().follower(user4).following(user1).build());
        followerFollowings.add(FollowerFollowing.builder().follower(user4).following(user2).build());

        followerFollowingRepository.saveAll(followerFollowings);

    }

    @GetMapping("/like")
    public void test2() {
        User user = userRepository.findById(Long.valueOf(1)).get();
        Route route = routeRepository.findById(Long.valueOf(1)).get();
        likeRepository.save(Like.builder().user(user).route(route).build());

        User user1 = userRepository.findById(Long.valueOf(2)).get();
        Route route1 = routeRepository.findById(Long.valueOf(1)).get();
        likeRepository.save(Like.builder().user(user1).route(route1).build());
    }

}





