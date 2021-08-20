package com.curation.backend.tag.dto;

import com.curation.backend.tag.domain.WithTag;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WithTagResponseDto {
    private Long id;
    private String title;

    public WithTagResponseDto(WithTag entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
    }
}
