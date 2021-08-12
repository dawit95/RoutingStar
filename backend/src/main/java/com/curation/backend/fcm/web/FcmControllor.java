package com.curation.backend.fcm.web;

import com.curation.backend.fcm.service.FcmService;
import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/push")
@RestController
public class FcmControllor {

    private final ResponseGenerateService responseGenerateService;
    private final FcmService fcmService;

    Logger logger = LoggerFactory.getLogger(FcmControllor.class);

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponseDto> setToken (@PathVariable("userId") Long userId,ServletRequest request, ServletResponse response) {
        String message = "";

        String browser_token = ((HttpServletRequest)request).getHeader("browser_token");
        logger.trace("브라우저토큰 왔음 {} ",browser_token);
        HttpStatus status;
        if(browser_token != null){
            message = fcmService.setBrowserToken(userId,browser_token);
            status = HttpStatus.OK;
        }else{
            message = "브라우저토큰이 null입니다....";
            status = HttpStatus.BAD_REQUEST;
        }
        logger.trace("브라우저토큰 저장 완료!!");

        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(message);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }
}
