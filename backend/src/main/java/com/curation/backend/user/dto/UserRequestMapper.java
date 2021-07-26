package com.curation.backend.user.dto;

import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserRequestMapper {

    Logger logger = LoggerFactory.getLogger(UserRequestMapper.class);

    public UserDto toDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        logger.debug("oAuth2User.getAttributes() : {}",attributes);
//        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        logger.debug("서브가 있는지 없는지 없을땐 이거 : {}",attributes.get("sub"));
        if(attributes.get("sub")!=null){
            return UserDto.builder()
                    .email((String)attributes.get("email"))
                    .name((String)attributes.get("name"))
                    .profileImg((String)attributes.get("picture"))
                    .build();
        }else{
            Map<String,Object> response = (Map<String, Object>)attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) response.get("profile");
            return UserDto.builder()
                    .name((String)profile.get("nickname"))
                    .email((String)response.get("email"))
                    .profileImg((String)profile.get("profile_image_url"))
                    .build();
        }
    }

//    public UserFindRequest toFindDto(UserDto oAuth2UserDto) {
//        return new UserFindRequest(oAuth2UserDto.getEmail());
//    }
}