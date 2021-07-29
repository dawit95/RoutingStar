package com.curation.backend.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SuccessDto {
    private Object success;

    @Builder
    public SuccessDto(Object success) {
        this.success = success;
    }
}
