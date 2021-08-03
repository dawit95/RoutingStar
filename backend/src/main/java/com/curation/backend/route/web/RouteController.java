package com.curation.backend.route.web;

import com.curation.backend.global.dto.ExceptionResponseDto;
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
    public Long addRoute(@RequestBody RouteRequestDto route) throws Exception {
        return routeService.save(route, route.getPlaces(), route.getWhatTag(), route.getWithTag());
    }

    @GetMapping("/routes/{id}")
    public ResponseEntity<List<RouteListResponseDto>> followingRouteList(@PathVariable("id") Long id) {
        List<RouteListResponseDto> list = routeService.followingRouteList(id);

        return new ResponseEntity<List<RouteListResponseDto>>(list, HttpStatus.OK);
    }

    @GetMapping("/routes")
    public ResponseEntity<List<RouteListResponseDto>> likeRouteList() {
        List<RouteListResponseDto> list = routeService.likeRouteList();
        return new ResponseEntity<List<RouteListResponseDto>>(list, HttpStatus.OK);

    }

    @GetMapping("/route/{id}")
    public ResponseEntity<RouteDetailResponseDto> routeDetail(@PathVariable("id") Long id) {
        RouteDetailResponseDto routeDetailResponseDto = routeService.getDetail(id);
        return new ResponseEntity<RouteDetailResponseDto>(routeDetailResponseDto, HttpStatus.OK);
    }

    @PutMapping("/route/{id}")
    public Long modifyRoute(@PathVariable("id") Long id, @RequestBody RouteRequestDto routeRequestDto) throws NoRouteException {
        return routeService.modifyRoute(id, routeRequestDto, routeRequestDto.getPlaces(), routeRequestDto.getWhatTag(), routeRequestDto.getWithTag());
    }

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
      "lang": "123.12",
      "lat": "231.56",
      "placeImg": "첫번째 장소의 이미지",
      "placeOrder": 1,
      "title": "첫번째 장소인 카페!"
    },
    {
      "lang": "567.12",
      "lat": "892.12",
      "placeImg": "두번째 장소의 이미지",
      "placeOrder": 2,
      "title": "두번째 장소인 산책로!"
    }
  ],
  "routeDescription": "루트에 대한 설명",
  "thumbnail": "루트의 썸네일",
  "whatTag": [
    1,3,5
  ],
  "withTag": [
    2,3,1
  ]
}
 */


/*
{
  "id": 1,
  "places": [
    {
      "lang": "999.9",
      "lat": "111.1",
      "placeImg": "새로생겨서 다시 등록",
      "placeOrder": 1,
      "title": "여기 강추강추"
    },
    {
      "lang": "999.9",
      "lat": "111.1",
      "placeImg": "하하호호 사진",
      "placeOrder": 2,
      "title": "카페 최고최고"
    },
    {
      "lang": "999.9",
      "lat": "111.1",
      "placeImg": "세번째 사진",
      "placeOrder": 3,
      "title": "여기는 밥집"
    }
  ],
  "routeDescription": "string",
  "thumbnail": "string",
  "whatTag": [
    4, 3, 1
  ],
  "withTag": [
    3, 4
  ]
}
 */