package com.curation.backend.route.web;

import com.curation.backend.global.dto.ExceptionResponseDto;
import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.route.dto.RouteDetailResponseDto;
import com.curation.backend.route.dto.RouteListResponseDto;
import com.curation.backend.route.dto.RouteRequestDto;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.route.service.RouteService;
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
    public ResponseEntity<SuccessResponseDto> followingRouteList(@PathVariable("userId") Long id) {
        List<RouteListResponseDto> list = routeService.followingRouteList(id);
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(list);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @GetMapping("/routes")
    public ResponseEntity<SuccessResponseDto> likeRouteList() {
        List<RouteListResponseDto> list = routeService.likeRouteList();
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(list);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);

    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<RouteDetailResponseDto> routeDetail(@PathVariable("routeId") Long id) throws NoRouteException {
        RouteDetailResponseDto routeDetailResponseDto = routeService.getDetail(id);
        return new ResponseEntity<RouteDetailResponseDto>(routeDetailResponseDto, HttpStatus.OK);
    }

    @PutMapping("/route/{routeId}")
    public Long modifyRoute(@PathVariable("routeId") Long id, @RequestBody RouteRequestDto routeRequestDto) throws NoRouteException {
        return routeService.modifyRoute(id, routeRequestDto, routeRequestDto.getPlaces(), routeRequestDto.getWhatTag(), routeRequestDto.getWithTag());
    }

    @DeleteMapping("/route/{routeId}")
    public ResponseEntity<SuccessResponseDto> deleteRoute(@PathVariable("routeId") Long id) throws NoRouteException {
        routeService.deleteRoute(id);
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse("성공적으로 삭제되었습니다.");
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

//    @GetMapping("/route/storage/{routeId}")
//    public ResponseEntity<SuccessResponseDto> getRouteStorageDetail(@PathVariable("routeId") Long id) {
//
//    }

    @ExceptionHandler(NoRouteException.class)
    public ResponseEntity<ExceptionResponseDto> noRouteHandler() {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String message = "해당하는 루트가 없습니다.";

        ExceptionResponseDto exceptionResponseDto = responseGenerateService.generateExceptionResponse(httpStatus, message);
        return new ResponseEntity<ExceptionResponseDto>(exceptionResponseDto, httpStatus);
    }
}

/*
{
  "id": 1,
  "places": [
    {
      "createdOrder": 1,
      "isThumbnail": true,
      "lat": "12.3",
      "lng": "45.2",
      "placeImg": "첫번째 사진이지롱",
      "title": "여기는 카페에요"
    },
    {
      "createdOrder": 2,
      "isThumbnail": false,
      "lat": "45.2222",
      "lng": "666.123",
      "placeImg": "두번째 사진이지롱",
      "title": "여기는 존맛 밥집"
    }
  ],
  "routeDescription": "우리동네 최고 코스에요",
  "routeImg": "루트 사진이 들어가있음",
  "whatTag": [
    1,2,3
  ],
  "withTag": [
    4,5,6
  ]
}
 */

/*
{
  "id": 1,
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
    1,1,3
  ],
  "withTag": [
    4,2,6,5
  ]
}
 */