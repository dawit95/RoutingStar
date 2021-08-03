package com.curation.backend.image.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.curation.backend.image.dto.ImgResponseDto;
import com.curation.backend.image.exception.ImageUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    Logger logger = LoggerFactory.getLogger(S3Uploader.class);

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public ImgResponseDto upload(MultipartFile files, String dirName) throws IOException, ImageUploadException {

        File uploadFile = convert(files).orElseThrow(() -> new ImageUploadException("이미지 업로드에 실패합니다."));

        String image = upload(uploadFile, dirName);
        ImgResponseDto imgResponseDto = new ImgResponseDto();
        imgResponseDto.setImage(image);

        return imgResponseDto;
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + LocalDateTime.now() + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        logger.trace("uploadImageUrl is " +  uploadImageUrl + "!!!!!");
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            logger.trace("File is deleted!!");
        } else {
            logger.trace("File cannot be deleted!!");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try(FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
