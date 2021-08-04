package com.curation.backend.place.service;

import com.curation.backend.place.domain.PlaceRepository;
import com.curation.backend.place.dto.PlacePositionDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private Logger logger = LoggerFactory.getLogger(PlaceService.class);


    public List<PlacePositionDto> getPlacePosition(Long id) {
        List<PlacePositionDto> list = placeRepository.findByRouteId(id).stream().map(PlacePositionDto::new).collect(Collectors.toList());
        return list;
    }
}

