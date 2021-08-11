package com.curation.backend.place.web;

import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.place.dto.PlacePositionDto;
import com.curation.backend.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/place")
@RestController
public class PlaceController {

    private final PlaceService placeService;
    private final ResponseGenerateService responseGenerateService;

    @GetMapping("/{routeId}")
    public ResponseEntity<SuccessResponseDto> getPlacePosition (@PathVariable("routeId") Long id) {
        List<PlacePositionDto> list = placeService.getPlacePosition(id);
        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(list);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }
}
