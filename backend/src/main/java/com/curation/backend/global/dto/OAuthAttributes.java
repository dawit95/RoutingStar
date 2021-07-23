package com.curation.backend.global.dto;


import com.curation.backend.user.domain.Role;
import com.curation.backend.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String profileImg;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String profileImg) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profileImg((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * User 엔티티 생성(OAuthAttributes에서 첫 가입할 때)
     * 가입할때 기본 권한 USER로 줌
     * @return User객체
     */
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .profileImg(profileImg)
                .role(Role.USER)
                .build();
    }
}
