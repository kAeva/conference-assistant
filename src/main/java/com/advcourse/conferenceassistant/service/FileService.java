package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.exception.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {



    public String uploadFile(MultipartFile file, String uploadDir) {

        try {
            Path copyLocation = Paths
                    .get(uploadDir + File.separator + UUID.randomUUID().toString()+"-"+ StringUtils.cleanPath(file.getOriginalFilename()));
             Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(copyLocation);
            return copyLocation.getFileName().toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }

    }
}
