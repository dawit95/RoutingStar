package com.curation.backend.global.exception;

import com.curation.backend.global.dto.ExceptionResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionController {

    private final ResponseGenerateService responseGenerateService;
    private final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<ExceptionResponseDto> handleEmptyResultDataAccess(EmptyResultDataAccessException e) {
        logger.error("EmptyResultDataAccessException", e);

        String message = "잘못된 데이터 접근입니다.";
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionResponseDto exceptionResponseDto = responseGenerateService.generateExceptionResponse(status, message);

        return new ResponseEntity<ExceptionResponseDto>(exceptionResponseDto, status);
    }
}
