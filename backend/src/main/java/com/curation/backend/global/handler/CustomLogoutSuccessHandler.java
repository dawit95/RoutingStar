package com.curation.backend.global.handler;

import com.curation.backend.fcm.service.FcmService;
import com.curation.backend.token.service.TokenService;
import com.curation.backend.user.domain.User;
import com.curation.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final FcmService fcmService;

    Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        logger.trace("CustomLogoutSuccessHandler.onLogoutSuccess ::::"); super.onLogoutSuccess(request, response, authentication);

        String access_token = request.getHeader("access_token");
        logger.trace("{}",request.getHeaderNames());
        logger.trace("?>>>>>>>>>>>>로그아웃 access_token: {}",access_token);

        if (access_token != null && tokenService.verifyToken(access_token)) {
            Long id = tokenService.getId(access_token);

            User user = userRepository.findById(id).orElse(null);

            if(user != null){
                user.updateRefreshToken("");
                userRepository.save(user);
                logger.trace("{}님의 refresh 초기화 했습니다.",tokenService.getNickName(access_token));

                String message = fcmService.setBrowserToken(id,"");

                logger.trace("{}님의 브라우저 토큰 {}으로 초기화 했습니다.",tokenService.getNickName(access_token),message);
            }
        } else {
            logger.trace("로그아웃 처리 실패!! 사실상 여기 오기 불가능...");
            //JWT요청이 잘못옴
            throw new IllegalArgumentException("JWT 토큰이 잘못되었습니다.");
        }
    }
}