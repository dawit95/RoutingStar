package com.curation.backend.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SuccessResponseDto {
    private Object success;

    @Builder
    public SuccessResponseDto(Object success) {
        this.success = success;
    }
}
