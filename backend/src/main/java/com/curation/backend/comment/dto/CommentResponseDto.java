package com.curation.backend.comment.dto;

import com.curation.backend.comment.domain.Comment;
import com.curation.backend.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentResponseDto {
    private Long id;
    private User user;
    private String comment;

    public CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.user = entity.getUser();
        this.comment = entity.getComment();
    }
}
