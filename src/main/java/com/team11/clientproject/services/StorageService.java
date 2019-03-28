package com.team11.clientproject.services;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String persistImage(MultipartFile file);

    boolean isImage(String filename);
}
