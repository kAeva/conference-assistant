package com.advcourse.conferenceassistant.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file, String uploadDir);

    String uploadFileToAWS(MultipartFile file);

    boolean deleteFileFromAWS(String path);

    String generateQrCode(String text, String uploadDir);

}
