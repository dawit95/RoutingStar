package com.curation.backend.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter @Setter
public class FFResponseDto {
    final private List<FollowerResponseDto> followerList;
    final private List<FollowingResponseDto> followingList;

}
