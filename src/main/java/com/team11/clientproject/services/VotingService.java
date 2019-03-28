package com.team11.clientproject.services;

import com.team11.clientproject.dtos.Encouragement;
import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.SampleImage;
import com.team11.clientproject.dtos.User;

import java.util.List;
import java.util.Optional;

public interface VotingService {
    Image getImageToVoteOn(User user);

    Optional<Image> getImageById(int id);

    void voteForImage(Image image, boolean isUp, User user);

    List<SampleImage> getSampleImages();

    Optional<Encouragement> getEncouragementForNumberOfImages(int number);

    void addImage(String path, Optional<Boolean> knownGood, List<String> additionalImagePaths);
}
