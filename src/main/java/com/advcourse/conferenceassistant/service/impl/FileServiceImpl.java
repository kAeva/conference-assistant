package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.exception.FileStorageException;
import com.advcourse.conferenceassistant.service.FileService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {



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
    public String generateQrCode(String text, String uploadPath) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        Path path = FileSystems.getDefault().getPath(uploadPath);
        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 350, 350);

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (WriterException e) {
           log.error("Could not generate QR Code ",e);
        }catch (IOException e) {
            e.printStackTrace();
        }

       return path.toString();
    }
}
