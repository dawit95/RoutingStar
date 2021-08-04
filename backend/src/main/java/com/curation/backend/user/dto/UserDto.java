package com.curation.backend.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String profileImg;

    @Builder
    public UserDto(Long id, String email, String name, String profileImg) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profileImg = profileImg;
    }
}
