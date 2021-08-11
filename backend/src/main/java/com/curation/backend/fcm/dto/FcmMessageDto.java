package com.curation.backend.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessageDto {
    private Data data;
    private String to;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Data{
        private String title;
        private String message;
    }

}