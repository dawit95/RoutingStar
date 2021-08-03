package com.curation.backend.token.web;

import com.curation.backend.token.domain.Token;
import com.curation.backend.token.service.TokenService;
import com.curation.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(TokenController.class);

    @GetMapping("/token/expired")
    public String auth()
    {
        logger.trace("token/expired요청함 : ");
        throw new RuntimeException();
    }

    @GetMapping("/token/refresh")
    public String refreshAuth(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Refresh");

        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            String img = tokenService.getProfileImg(token);
            String name = tokenService.getNickName(token);
            Token newToken = tokenService.generateToken(email,img,name, "USER");



            response.addHeader("Auth", newToken.getToken());
            response.addHeader("Refresh", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "HAPPY NEW TOKEN";
        }


        logger.trace("token/refresh 요청함 : {}",token);

        throw new RuntimeException();
    }

}