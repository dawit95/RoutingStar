package com.curation.backend.user.dto;

import com.curation.backend.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private String profileImg;
    private String userDescription;
    private Boolean followed;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.profileImg = entity.getProfileImg();
        this.userDescription= entity.getUserDescription();
    }

    @Override
    public String toString() {
        return this.id + " : " + this.email + " : " + this.name + " : " + this.profileImg + " : " + this.userDescription +" : "+this.followed;
    }
}
