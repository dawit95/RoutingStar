package com.curation.backend.token.web;

import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.token.domain.Token;
import com.curation.backend.token.service.TokenService;
import com.curation.backend.user.domain.User;
import com.curation.backend.user.domain.UserRepository;
import com.curation.backend.user.exception.NoUserException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class TokenController {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final ResponseGenerateService responseGenerateService;

    Logger logger = LoggerFactory.getLogger(TokenController.class);

    @GetMapping("/token/expired")
    public String auth()
    {
        logger.trace("token/expired요청함 : ");
        throw new RuntimeException();
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<SuccessResponseDto> refreshAuth(HttpServletRequest request, HttpServletResponse response) throws NoUserException {
        String access_token = request.getHeader("access_token");
        String refresh_token = request.getHeader("refresh_token");

        if (refresh_token != null && tokenService.verifyToken(refresh_token)) {
            String email = tokenService.getUid(access_token);
            String img = tokenService.getProfileImg(access_token);
            String name = tokenService.getNickName(access_token);
            Long id = tokenService.getId(access_token);

            User user = userRepository.findById(id).orElseThrow(() -> new NoUserException("토큰의 사용자가 없습니다."));

            if(user.getRefreshToken().equals(refresh_token)){
                logger.trace("저장된 re와 보낸 re가 일치 합니다.");

                Token newToken = tokenService.generateToken(user.getId(), email, img, name, "USER");

//                response.addHeader("access_token", newToken.getAccess_token());
//                response.setContentType("application/json;charset=UTF-8");
                HttpStatus status = HttpStatus.OK;
                SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(newToken.getAccess_token());

                return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
            } else {
                logger.trace("저장된 re와 보낸 re가 불일치 합니다.");
                logger.trace("저장된 re {}",user.getRefreshToken());
                logger.trace("보낸 re {}",refresh_token);
                throw new IllegalArgumentException("JWT 토큰이 잘못되었습니다.");
            }
        } else {
            //JWT요청이 잘못옴
            throw new IllegalArgumentException("JWT 토큰이 잘못되었습니다.");
        }
    }

}