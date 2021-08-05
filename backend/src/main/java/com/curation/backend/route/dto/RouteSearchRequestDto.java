package com.curation.backend.route.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RouteSearchRequestDto {
    private List<Long> whatTag;
    private List<Long> withTag;
}
