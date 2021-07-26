package com.curation.backend.image.web;

import com.curation.backend.image.dto.ImgRequestDto;
import com.curation.backend.image.dto.ImgResponseDto;
import com.curation.backend.image.exception.ImageUploadException;
import com.curation.backend.image.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/v1/img")
@RequiredArgsConstructor
@RestController
public class S3Controller {

    private Logger logger = LoggerFactory.getLogger(S3Controller.class);
    private final S3Uploader s3Uploader;

    @PostMapping("/place")
    public ImgResponseDto upload(@RequestPart List<MultipartFile> files) throws IOException, ImageUploadException {
    logger.trace("in!!");
    if(files == null)    logger.trace("is null");
    else                    logger.trace("is not null");

    logger.trace(String.valueOf(files.size()));
//            logger.trace("/api/v1/img/place is called!!!!!!" + images.size());
//        return s3Uploader.upload(image, "static");
//        for(MultipartFile e : images) {
//            if(e == null) logger.trace("is null!!!!");
//            else            logger.trace("is not null!!!");
//        }
        return null;
    }

}
