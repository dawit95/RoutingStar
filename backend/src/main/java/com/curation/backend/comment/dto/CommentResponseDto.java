package com.curation.backend.comment.dto;

import com.curation.backend.comment.domain.Comment;
import com.curation.backend.user.domain.User;
import com.curation.backend.user.dto.UserResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentResponseDto {
    private Long id;
    private UserResponseDto user;
    private String comment;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.user = new UserResponseDto(entity.getUser());
        this.comment = entity.getComment();
        this.createdAt = entity.getCreatedAt();
    }
}
