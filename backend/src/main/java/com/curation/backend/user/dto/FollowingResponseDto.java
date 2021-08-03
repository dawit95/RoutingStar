package com.curation.backend.user.dto;

import com.curation.backend.user.domain.FollowerFollowing;
import lombok.Getter;

@Getter
public class FollowingResponseDto {
    UserResponseDto following;

    public FollowingResponseDto(FollowerFollowing entity) {
        this.following = new UserResponseDto(entity.getFollowing());
    }
}