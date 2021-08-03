package com.curation.backend.user.dto;

import com.curation.backend.user.domain.FollowerFollowing;
import lombok.Getter;

@Getter
public class FollowerResponseDto {

    UserResponseDto follower;

    public FollowerResponseDto(FollowerFollowing entity) {
        this.follower = new UserResponseDto(entity.getFollower());
    }
}
