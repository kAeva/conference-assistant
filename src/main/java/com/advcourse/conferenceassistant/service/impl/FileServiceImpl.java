package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.exception.FileStorageException;
import com.advcourse.conferenceassistant.service.FileService;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
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
            String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucket, fileName, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucket, fileName));
            return s3Object.getObjectContent().getHttpRequest().getURI().toString();
        } catch (IOException e) {
            log.error("Could not upload file. Error", e);
            return "";
        }

    }

    @Override
    public boolean deleteFileFromAWS(String path) {
        try {
            String key = path.substring(path.lastIndexOf("amazonaws.com") + 14);
            s3Client.deleteObject(new DeleteObjectRequest(bucket, key));
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            log.info("Amazon couldn't delete file. Error {} ", e.getMessage());
            return false;
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            log.info("Amazon couldn't delete file. Error {} ", e.getMessage());
            return true;
        }
        return true;


    }

    public String generateQrCode(String text, String uploadPath) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        Path path = FileSystems.getDefault().getPath(uploadPath);
        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 350, 350);

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (WriterException e) {
            log.error("Could not generate QR Code ", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path.toString();
    }
}
