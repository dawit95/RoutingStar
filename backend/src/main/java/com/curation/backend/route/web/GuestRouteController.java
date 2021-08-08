package com.curation.backend.route.web;

import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.route.dto.RouteDetailResponseDto;
import com.curation.backend.route.dto.RouteDetailWithCommentResponseDto;
import com.curation.backend.route.dto.RouteListResponseDto;
import com.curation.backend.route.exception.NoRouteException;
import com.curation.backend.route.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/guest")
@RequiredArgsConstructor
@RestController
public class GuestRouteController {

    private final RouteService routeService;
    private final ResponseGenerateService responseGenerateService;

    @GetMapping("/routes")
    public ResponseEntity<SuccessResponseDto> likeRouteList() {
        List<RouteListResponseDto> list = routeService.likeRouteList();
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(list);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }

    @GetMapping("/route/{routeId}")
    public ResponseEntity<SuccessResponseDto> routeDetailWithComment(@PathVariable("routeId") Long id) throws NoRouteException {
        RouteDetailWithCommentResponseDto routeDetailResponseDto = routeService.getDetailWithComment(id);
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(routeDetailResponseDto);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, HttpStatus.OK);
    }

    @GetMapping("/mypage/routes/{userId}")
    public ResponseEntity<SuccessResponseDto> myRouteList(@PathVariable("userId") Long id) {
        List<RouteListResponseDto> list = routeService.myRouteList(id);
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(list);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }
}
