package com.curation.backend.image.web;

import com.curation.backend.global.dto.ErrorDto;
import com.curation.backend.global.dto.ExceptionResponseDto;
import com.curation.backend.global.dto.SuccessDto;
import com.curation.backend.global.dto.SuccessResponseDto;
import com.curation.backend.global.service.ResponseGenerateService;
import com.curation.backend.image.dto.ImgResponseDto;
import com.curation.backend.image.exception.ImageUploadException;
import com.curation.backend.image.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RequestMapping("/api/v1/img")
@RequiredArgsConstructor
@RestController
public class S3Controller {

    private Logger logger = LoggerFactory.getLogger(S3Controller.class);
    private final S3Uploader s3Uploader;
    private final ResponseGenerateService responseGenerateService;

    @PostMapping("/place")
    public ResponseEntity<SuccessResponseDto> upload(@RequestPart MultipartFile files) throws IOException, ImageUploadException {
        logger.trace("in!!");

        if(files == null)    logger.trace("is null");
        else                 logger.trace("is not null");

        ImgResponseDto imgResponseDto = s3Uploader.upload(files, "");
        SuccessResponseDto successResponseDto = responseGenerateService.generateSuccessResponse(imgResponseDto);

        return new ResponseEntity<SuccessResponseDto>(successResponseDto, HttpStatus.OK);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponseDto> nullPointerHandler() {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String message = "등록하려는 사진이 없습니다.";

        ExceptionResponseDto exceptionResponseDto = responseGenerateService.generateExceptionResponse(httpStatus, message);
        return new ResponseEntity<ExceptionResponseDto>(exceptionResponseDto, httpStatus);
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<ExceptionResponseDto> imageUploadHanlder() {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String message = "사진을 변환하는 과정 중에 문제가 발생했습니다.";

        ExceptionResponseDto exceptionResponseDto = responseGenerateService.generateExceptionResponse(httpStatus, message);
        return new ResponseEntity<ExceptionResponseDto>(exceptionResponseDto, httpStatus);
    }

}
