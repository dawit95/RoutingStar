package com.curation.backend.global;

import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.route.dto.RouteListResponseDto;
import com.curation.backend.route.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/userTest")
@RequiredArgsConstructor
@RestController
public class TestController {
    private final RouteService routeService;
    private final ResponseGenerateService responseGenerateService;

    @GetMapping("/routes/{userId}")
    public ResponseEntity<SuccessResponseDto> followingRouteList(@PathVariable("userId") Long id) {
        List<RouteListResponseDto> list = routeService.followingRouteList(id);
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(list);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }
}
