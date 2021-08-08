package com.curation.backend.comment.dto;

import com.curation.backend.comment.domain.Comment;
import com.curation.backend.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentRequestDto {
    private Long id;    //등록하는 사용자의 아이디값
    private String comment;

    public Comment toEntity() {
        return Comment.builder().comment(this.comment).build();
    }
}
