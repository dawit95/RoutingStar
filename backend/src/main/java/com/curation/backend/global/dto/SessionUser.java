package com.curation.backend.global.dto;

import com.curation.backend.user.domain.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String profileImg;
//    private String userDescription;
//    private String fileName;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImg = user.getProfileImg();
//        this.userDescription = user.getUserDescription();
//        this.fileName = user.getFileName();
    }
}
