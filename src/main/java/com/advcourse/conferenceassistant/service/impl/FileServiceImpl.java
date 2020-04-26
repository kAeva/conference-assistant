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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${s3.img.speaker.bucket}")
    String bucket;

    @Value("${s3.qrcode.bucket}")
    String qrCodeBucket;

    /**
     * Upload file and save it in the specified location.
     * The path argument is location where needed save file.
     *
     * @param file the file which need to upload
     * @param path A pathname string
     * @return file name.
     */
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
    /**
     * Upload file into the Amazon AWS S3 Bucket. Where bucket name is "nomic-speaker-bucket".
     *
     * @param file the file which need to upload
     * @return absolute path to saved image.
     */
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

    /**
     * Delete file from Amazon AWS S3 Bucket. Where bucket name is "nomic-speaker-bucket".
     *
     * @param path absolute path to image.
     * @return true if the file has deleted.
     */
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
            return false;
        }
        return true;


    }

    /**
     * Method writes the QRCode image into Amazon AWS S3 Bucket, where bucket name is "nomic-qrcode-bucket".
     * This method takes the text to be encoded, the width and height of the QR Code are 350 px,
     * and returns the absolute path to saved image. The name of saved image consist with
     * "conferenceId=" and confId argument.
     *
     * @param text   the text to be encoded
     * @param confId the conference ID
     * @return absolute path to saved image.
     */
    public String generateQrCode(String text, Long confId) {
        try {
            if (!s3Client.doesBucketExist(qrCodeBucket)) {
                // Because the CreateBucketRequest object doesn't specify a region, the
                // bucket is created in the region specified in the client.
                s3Client.createBucket(new CreateBucketRequest(qrCodeBucket));
            }

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it and returned an error response.
            log.info("Amazon couldn't create new bucket. Error {} ", e.getMessage());
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            log.info("Amazon couldn't create new bucket. Error {} ", e.getMessage());
        }
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;

        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 350, 350);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", os);
            byte[] buffer = os.toByteArray();
            String qrCodeFileName = "conferenceId=" + confId;
            InputStream is = new ByteArrayInputStream(buffer);
            s3Client.putObject(new PutObjectRequest(qrCodeBucket, qrCodeFileName, is, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(qrCodeBucket, qrCodeFileName));
            return s3Object.getObjectContent().getHttpRequest().getURI().toString();
        } catch (WriterException e) {
            log.error("Could not generate QR Code ", e);
            return "";
        } catch (IOException e) {
            log.error("Could not write img ", e);
            return "";
        }


    }
}
