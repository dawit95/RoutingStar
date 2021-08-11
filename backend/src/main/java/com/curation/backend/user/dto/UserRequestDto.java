package com.curation.backend.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserRequestDto {
    private Long id;
    private String name;
    private String profileImg;
    private String userDescription;


}
