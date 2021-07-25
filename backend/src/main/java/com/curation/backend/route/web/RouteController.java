package com.curation.backend.route.web;

import com.curation.backend.place.domain.Place;
import com.curation.backend.place.dto.PlaceRequestDto;
import com.curation.backend.route.dto.RouteRequestDto;
import com.curation.backend.route.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/route")
@RequiredArgsConstructor
@RestController
public class RouteController {

    Logger logger = LoggerFactory.getLogger(RouteController.class);

    private final RouteService routeService;

    @PostMapping("")
    public Long addRoute(@RequestBody RouteRequestDto route) throws Exception {
        logger.trace(route.getPlaces().size() + "만큼 들어왔다.");
        return routeService.save(route, route.getPlaces());
    }
}
