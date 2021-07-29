package com.curation.backend.global.handler;

import com.curation.backend.token.domain.Token;
import com.curation.backend.token.service.TokenService;
import com.curation.backend.user.domain.User;
import com.curation.backend.user.domain.UserRepository;
import com.curation.backend.user.dto.UserDto;
import com.curation.backend.user.dto.UserRequestMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * OAuth2의 인증 공급자로부터 인증이 성공한 후 취득한 사용자 정보를 처리하는 핸들러
 * 이 핸들러에서 다시 JWT를 발급한다
 * @author Dawit
 *
 */

@RequiredArgsConstructor
@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;
    private final ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(CustomOAuth2SuccessHandler.class);


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        UserDto userDto = userRequestMapper.toDto(oAuth2User);

//        // 최초 로그인이라면 회원가입 처리를 한다.
//        if(!userRepository.existsByEmail(userDto.getEmail())){
//
//        }

        Token token = tokenService.generateToken(userDto.getEmail(),userDto.getProfileImg(), userDto.getName(), "USER");
        logger.debug("{}", token);

        //회원 테이블에 삽입
        User user = userRepository.findByEmail(userDto.getEmail()).get();
        logger.trace(user.getRefreshToken() + "is before!!!");
        user.updateRefreshToken(token.getRefreshToken());
        userRepository.save(user);
        logger.trace(user.getRefreshToken() + "is after!!!");

        writeTokenResponse(response, token);
        logger.trace("reponse uri : {}", Arrays.toString(response.getHeaderNames().toArray(new String[0])));
    }

    private void writeTokenResponse(HttpServletResponse response, Token token)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}