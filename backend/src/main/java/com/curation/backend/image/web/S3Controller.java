package com.curation.backend.image.web;

import com.curation.backend.image.dto.ImgResponseDto;
import com.curation.backend.image.exception.ImageUploadException;
import com.curation.backend.image.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/v1/img")
@RequiredArgsConstructor
@RestController
public class S3Controller {

    private Logger logger = LoggerFactory.getLogger(S3Controller.class);
    private final S3Uploader s3Uploader;

    @PostMapping("/place")
    public ImgResponseDto upload(@RequestParam("data")MultipartFile multipartFile) throws IOException, ImageUploadException {
        logger.trace("/api/v1/img/place is called!!!!!! " + multipartFile.isEmpty());
        return s3Uploader.upload(multipartFile, "static");
    }

}
