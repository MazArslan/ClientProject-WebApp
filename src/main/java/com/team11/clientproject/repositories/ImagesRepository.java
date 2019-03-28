package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.dtos.ImageDescription;
import java.util.List;
import java.util.Optional;

public interface ImagesRepository {
    Image getImage(User user);

    Optional<Image> getImageById(int id);

    Double getImageRanking(Image image);

    List<Image> getAllImages();

    List<ImageDescription> getAllImagesDescriptions();

    void addImage(String path, Boolean trustworthy, List<String> additonalImagePaths);
}
