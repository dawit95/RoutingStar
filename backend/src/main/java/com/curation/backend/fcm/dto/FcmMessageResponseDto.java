package com.curation.backend.fcm.dto;

import com.curation.backend.fcm.domain.FcmMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
public class FcmMessageResponseDto {

    private Long id;
    private Long toUser;
    private Long fromUser;
    private String title;
    private String content;
    private int isPush;
    private LocalDateTime createTime;

    public FcmMessageResponseDto(FcmMessage entity) {
        this.id = entity.getId();
        this.toUser = entity.getToUser();
        this.fromUser = entity.getFromUser();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.isPush= entity.getIsPush();
        this.createTime = entity.getCreatedAt();
    }
}