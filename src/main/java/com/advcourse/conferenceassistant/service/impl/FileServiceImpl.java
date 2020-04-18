package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.exception.FileStorageException;
import com.advcourse.conferenceassistant.service.FileService;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${jsa.s3.bucket}")
    String bucket;

    @Override
    public String uploadFile(MultipartFile file, String path) {
        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            log.info("Dir isn't exists : " + uploadDir.getAbsolutePath());
            boolean mkdir = uploadDir.mkdirs();
            log.info("Create dir {}", mkdir);
        }
        try {
            Path copyLocation = Paths
                    .get(uploadDir.getAbsolutePath() + File.separator + UUID.randomUUID().toString() + "-" + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Save file {} to {}", file.getOriginalFilename(), copyLocation.toString());
            return copyLocation.getFileName().toString();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }

    }

    @Override
    public String uploadFileToAWS(MultipartFile file) {

        try {
            InputStream is = file.getInputStream();
            String fileName = UUID.randomUUID().toString()+file.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucket, fileName, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucket, fileName));
            return s3Object.getObjectContent().getHttpRequest().getURI().toString();
        } catch (IOException e) {
            log.error("Could not upload file. Error", e);

        }
        return "";
    }
}
