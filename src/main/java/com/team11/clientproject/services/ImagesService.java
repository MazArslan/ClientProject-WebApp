package com.team11.clientproject.services;

import com.team11.clientproject.dtos.AdminImageDTO;
import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.ImageDescription;

import java.util.List;

public interface ImagesService {
    List<Image> getAllImages();

    AdminImageDTO getAdminImageDTO(Image image);

    List<ImageDescription> getImagesDescription();
}
