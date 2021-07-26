package com.curation.backend.route.web;

import com.curation.backend.route.dto.RouteDetailResponseDto;
import com.curation.backend.route.dto.RouteListResponseDto;
import com.curation.backend.route.dto.RouteRequestDto;
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

    @PostMapping("/route")
    public Long addRoute(@RequestBody RouteRequestDto route) throws Exception {
        return routeService.save(route, route.getPlaces());
    }

    @GetMapping("/routes/{id}")
    public ResponseEntity<List<RouteListResponseDto>> followingRouteList(@PathVariable("id") Long id) {
        List<RouteListResponseDto> list = routeService.followingRouteList(id);

        return new ResponseEntity<List<RouteListResponseDto>>(list, HttpStatus.OK);
    }

    @GetMapping("/route/{id}")
    public ResponseEntity<RouteDetailResponseDto> routeDetail(@PathVariable("id") Long id) {
        RouteDetailResponseDto routeDetailResponseDto = routeService.getDetail(id);
        return new ResponseEntity<RouteDetailResponseDto>(routeDetailResponseDto, HttpStatus.OK);
    }
}
