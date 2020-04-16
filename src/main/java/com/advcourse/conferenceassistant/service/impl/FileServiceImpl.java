package com.advcourse.conferenceassistant.service.impl;

import com.advcourse.conferenceassistant.exception.FileStorageException;
import com.advcourse.conferenceassistant.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
}
