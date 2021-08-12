package com.curation.backend.fcm.web;

import com.curation.backend.fcm.service.FcmService;
import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("{userId}")
    public ResponseEntity<SuccessResponseDto> setToken (@PathVariable("userId") Long userId,ServletRequest request, ServletResponse response) {
        String message = "";

        String browser_token = ((HttpServletRequest)request).getHeader("browser_token");

        message = fcmService.setBrowserToken(userId,browser_token);

        HttpStatus status = HttpStatus.OK;
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(message);
        return new ResponseEntity<SuccessResponseDto>(successResponseDto, status);
    }
}
