package com.curation.backend.user.web;

import com.curation.backend.fcm.service.PushService;
import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.route.dto.RouteDetailResponseDto;
import com.curation.backend.route.dto.RouteListResponseDto;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.route.service.RouteService;
import com.curation.backend.tag.domain.WhatTag;
import com.curation.backend.tag.domain.WhatTagRepository;
import com.curation.backend.tag.domain.WithTag;
import com.curation.backend.tag.domain.WithTagRepository;
import com.curation.backend.token.service.TokenService;
import com.curation.backend.user.domain.FollowerFollowing;
import com.curation.backend.user.domain.FollowerFollowingRepository;
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

    private final UserService userService;
    private final ResponseGenerateService responseGenerateService;

    private final WhatTagRepository whatTagRepository;
    private final WithTagRepository withTagRepository;
    private final FollowerFollowingRepository followerFollowingRepository;

    private final TokenService tokenService;
    private final RouteService routeService;
    private final PushService pushService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/info/{targetId}")
    public ResponseEntity<SuccessResponseDto> getUserInfo(@PathVariable Long targetId, ServletRequest request) throws NoUserException, NoRouteException {

        UserResponseDto userResponseDto =  userService.getUserInfo(targetId);
        logger.trace("유저는 찾았다. {} ",userResponseDto);
        String token = ((HttpServletRequest)request).getHeader("access_token");
        logger.trace("access_token도 잘왔다. 찾았다. {} ",token);
        if (token != null) {
            Long userId = tokenService.getId(token);

            FollowerFollowing followed = followerFollowingRepository.findByFollower_IdAndAndFollowing_Id(targetId,userId).orElse(null);
            FollowerFollowing following = followerFollowingRepository.findByFollower_IdAndAndFollowing_Id(userId, targetId).orElse(null);
            logger.trace("{}가 {}의 follow를 눌렀다. {} ",userId,targetId,userResponseDto);
            //follow정보 저장
            userResponseDto.setFollowed(followed != null?true:false);
            userResponseDto.setFollowing(following != null?true:false);
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

    @GetMapping("/tag")
    public void test() {

        List<WhatTag> whatTags = new ArrayList<>();
        whatTags.add(WhatTag.builder().title("식사하기").build());
        whatTags.add(WhatTag.builder().title("산책하기").build());
        whatTags.add(WhatTag.builder().title("운동하기").build());
        whatTags.add(WhatTag.builder().title("여행하기").build());
        whatTags.add(WhatTag.builder().title("힐링하기").build());
        whatTags.add(WhatTag.builder().title("체험하기").build());
        whatTags.add(WhatTag.builder().title("학습하기").build());

        whatTagRepository.saveAll(whatTags);

        List<WithTag> withTags = new ArrayList<>();
        withTags.add(WithTag.builder().title("나혼자").build());
        withTags.add(WithTag.builder().title("친구").build());
        withTags.add(WithTag.builder().title("가족").build());
        withTags.add(WithTag.builder().title("반려동물").build());
        withTags.add(WithTag.builder().title("연인").build());
        withTags.add(WithTag.builder().title("아이").build());
        withTags.add(WithTag.builder().title("낯선사람").build());

        withTagRepository.saveAll(withTags);

    }

}





