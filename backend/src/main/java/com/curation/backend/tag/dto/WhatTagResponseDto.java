package com.curation.backend.tag.dto;

import com.curation.backend.tag.domain.WhatTag;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WhatTagResponseDto {
    private Long id;
    private String title;

    public WhatTagResponseDto(WhatTag entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
    }
}
