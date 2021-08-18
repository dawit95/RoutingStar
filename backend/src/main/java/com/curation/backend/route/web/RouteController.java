package com.curation.backend.route.web;

import com.curation.backend.global.dto.ExceptionResponseDto;
import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.route.dto.*;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.route.service.RouteService;
import com.curation.backend.user.exception.NoUserException;
import com.google.api.Http;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class RouteController {

    Logger logger = LoggerFactory.getLogger(RouteController.class);

    private final RouteService routeService;
    private final ResponseGenerateService responseGenerateService;

    @PostMapping("/route")
    public ResponseEntity<SuccessResponseDto> addRoute(@RequestBody RouteRequestDto route) throws Exception {
        Long id = routeService.save(route, route.getPlaces(), route.getWhatTag(), route.getWithTag());
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(id);

        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @GetMapping("/routes/{userId}")
    public ResponseEntity<SuccessResponseDto> followingRouteList(@PathVariable("userId") Long id) throws NoUserException {
        List<RouteListResponseDto> list = routeService.followingRouteList(id);
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(list);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @GetMapping("/routes/{userId}/{routeId}")
    public ResponseEntity<SuccessResponseDto> routeDetailWithComment(@PathVariable("userId") Long userId, @PathVariable("routeId") Long id) throws NoRouteException, NoUserException {
        RouteDetailWithCommentResponseDto routeDetailWithCommentResponseDto = routeService.getDetailWithComment(userId, id);
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(routeDetailWithCommentResponseDto);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @PutMapping("/route/{routeId}")
    public ResponseEntity<SuccessResponseDto> modifyRoute(@PathVariable("routeId") Long id, @RequestBody RouteRequestDto routeRequestDto) throws NoRouteException {
        Long routeId = routeService.modifyRoute(id, routeRequestDto, routeRequestDto.getPlaces(), routeRequestDto.getWhatTag(), routeRequestDto.getWithTag());
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(routeId);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

        @DeleteMapping("/route/{userId}/{routeId}")
    public ResponseEntity<SuccessResponseDto> deleteRoute(@PathVariable("userId") Long userId, @PathVariable("routeId") Long id) throws NoRouteException {
        routeService.deleteRoute(userId, id);
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse("성공적으로 삭제되었습니다.");
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @PostMapping("/following/routes/{userId}")
    public ResponseEntity<SuccessResponseDto> searchFollowingRoute(@PathVariable("userId") Long id, @RequestBody RouteSearchRequestDto routeSearchRequestDto) throws NoUserException {
        List<RouteListResponseDto> list = routeService.searchFollowingRoute(id, routeSearchRequestDto.getWhatTag(), routeSearchRequestDto.getWithTag());
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(list);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @PostMapping("/nonfollowing/routes/{userId}")
    public ResponseEntity<SuccessResponseDto> searchNonFollowingRoute(@PathVariable("userId") Long id, @RequestBody RouteSearchRequestDto routeSearchRequestDto) throws NoUserException {
        List<RouteListResponseDto> list = routeService.searchNonFollowingRoute(id, routeSearchRequestDto.getWhatTag(), routeSearchRequestDto.getWithTag());
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(list);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    //수정시 사용
    @GetMapping("/route/{userId}/{routeId}")
    public ResponseEntity<SuccessResponseDto> routeDetail(@PathVariable("userId") Long userId, @PathVariable("routeId") Long id) throws NoRouteException {
        RouteDetailResponseDto routeDetailResponseDto = routeService.getDetail(userId, id);
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(routeDetailResponseDto);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, HttpStatus.OK);
    }
}

/*
{
  "id": 1,
  "places": [
    {
      "createdOrder": 1,
      "isThumbnail": true,
      "lat": 12.3,
      "lng": 45.2,
      "placeImg": "첫번째 사진이지롱",
      "title": "여기는 카페에요"
    },
    {
      "createdOrder": 2,
      "isThumbnail": false,
      "lat": 45.2222,
      "lng": 666.123,
      "placeImg": "두번째 사진이지롱",
      "title": "여기는 존맛 밥집"
    }
  ],
  "routeDescription": "우리동네 최고 코스에요",
  "routeImg": "루트 사진이 들어가있음",
  "whatTag": [
    1, 2, 3
  ],
  "withTag": [
    1, 2, 7
  ]
}
 */

/*
{
  "id": 3,
  "places": [
    {
      "createdOrder": 1,
      "isThumbnail": false,
      "lat": "772.12",
      "lng": "452.123",
      "placeImg": "수정한 이미지 1",
      "title": "여기는 영화관"
    },
    {
      "createdOrder": 2,
      "isThumbnail": false,
      "lat": "7.123",
      "lng": "8.23",
      "placeImg": "수정한 이미지 2",
      "title": "여기는 영화관"
    },
    {
      "createdOrder": 3,
      "isThumbnail": true,
      "lat": "478.1",
      "lng": "98.1",
      "placeImg": "수정한 이미지 3",
      "title": "여기는 영화관"
    }
  ],
  "routeDescription": "수정해버리기 이 코스는 우리동네 맛집코스",
  "routeImg": "루트 사진도 수정함",
  "whatTag": [
    2
  ],
  "withTag": [
    3
  ]
}


{
  "id": 2,
  "places": [
    {
      "createdOrder": 1,
      "isThumbnail": false,
      "lat": "772.12",
      "lng": "452.123",
      "placeImg": "수정한 이미지 1",
      "title": "여기는 영화관"
    },
    {
      "createdOrder": 2,
      "isThumbnail": false,
      "lat": "7.123",
      "lng": "8.23",
      "placeImg": "수정한 이미지 2",
      "title": "여기는 영화관"
    },
    {
      "createdOrder": 3,
      "isThumbnail": true,
      "lat": "478.1",
      "lng": "98.1",
      "placeImg": "수정한 이미지 3",
      "title": "여기는 영화관"
    }
  ],
  "routeDescription": "3번째로 올린 글",
  "routeImg": "루트 사진도 수정함",
  "whatTag": [
    5, 2
  ],
  "withTag": [
    1, 4
  ]
}
 */