package com.team11.clientproject.services;

import com.team11.clientproject.dtos.AdminImageDTO;
import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.ImageDescription;
import com.team11.clientproject.repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesServiceImpl implements ImagesService {

    private final ImagesRepository imagesRepository;

    @Autowired
    public ImagesServiceImpl(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    @Override
    public List<Image> getAllImages() {
        return imagesRepository.getAllImages();
    }

    /**
     * Gets the Adminimagedto for an image
     *
     * @param image the image to get it from
     * @return the admin image DTO.
     */
    @Override
    public AdminImageDTO getAdminImageDTO(Image image) {
        var ranking = imagesRepository.getImageRanking(image);
        var adminImage = new AdminImageDTO();
        adminImage.setId(image.getId());
        adminImage.setPath(image.getPath());
        adminImage.setRanking(ranking);
        return adminImage;
    }

    /**
     * Gets the description for all images
     *
     * @return the list of images's descriptions
     */
    @Override
    public List<ImageDescription> getImagesDescription() {
        return imagesRepository.getAllImagesDescriptions();
    }
}
