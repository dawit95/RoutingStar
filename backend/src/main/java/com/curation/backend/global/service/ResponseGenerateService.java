package com.curation.backend.global.service;

import com.curation.backend.global.dto.ErrorDto;
import com.curation.backend.global.dto.ExceptionResponseDto;
import com.curation.backend.global.dto.SuccessDto;
import com.curation.backend.global.dto.SuccessResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResponseGenerateService {

    public ExceptionResponseDto generateExceptionResponse(HttpStatus httpStatus, String message) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();

        ErrorDto errorDto = ErrorDto.builder().code(httpStatus).message(message).build();
        exceptionResponseDto.setError(errorDto);

        return exceptionResponseDto;
    }

    public SuccessResponseDto generateSuccessResponse(Object data) {
        SuccessResponseDto successResponseDto = SuccessResponseDto.builder().success(data).build();
        return successResponseDto;
    }

}
