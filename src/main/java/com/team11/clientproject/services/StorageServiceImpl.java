package com.team11.clientproject.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {
    @Value("${files.upload_path}")
    private String path;
    @Value("${files.access_path}")
    private String webPath;
    private String[] allowedExtensionsImages = new String[]{"png", "jpg", "jpeg"};

    @Override
    public String persistImage(MultipartFile file) {
        if (isImage(file.getOriginalFilename())) {
            var filename = UUID.randomUUID() + "." + getFileExtension(file.getOriginalFilename());
            Path p = Paths.get(path + filename);
            try {
                Files.write(p, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return webPath + filename;
        }
        throw new IllegalArgumentException("Can't access image!");
    }

    private String getFileExtension(String filename) {
        var split = filename.split("\\.");
        return split[split.length - 1];
    }

    @Override
    public boolean isImage(String filename) {
        var extension = getFileExtension(filename);
        return Arrays.asList(allowedExtensionsImages).contains(extension);
    }
}
