package com.team11.clientproject.services;

import com.team11.clientproject.dtos.Encouragement;
import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.SampleImage;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.EncouragementRepository;
import com.team11.clientproject.repositories.ImagesRepository;
import com.team11.clientproject.repositories.SampleImagesRepository;
import com.team11.clientproject.repositories.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service that deals with voting on images.
 */
@Service
public class VotingServiceImpl implements VotingService {
    private final AchievementService achievementService;
    private ImagesRepository imagesRepository;
    private VotesRepository votesRepository;
    private SampleImagesRepository sampleImagesRepository;
    private EncouragementRepository encouragementRepository;

    @Autowired
    public VotingServiceImpl(ImagesRepository imagesRepository, VotesRepository votesRepository, SampleImagesRepository sampleImagesRepository, EncouragementRepository encouragementRepository, AchievementService achievementService) {
        this.imagesRepository = imagesRepository;
        this.votesRepository = votesRepository;
        this.sampleImagesRepository = sampleImagesRepository;
        this.encouragementRepository = encouragementRepository;
        this.achievementService = achievementService;
    }

    /**
     * Gets an image the user should vote on
     *
     * @return image to be voted on.
     */
    @Override
    public Image getImageToVoteOn(User user) {
        return imagesRepository.getImage(user);
    }

    /**
     * Returns an image by its id
     *
     * @param id the ID of the image
     * @return the image
     */
    @Override
    public Optional<Image> getImageById(int id) {
        return imagesRepository.getImageById(id);
    }

    /**
     * Votes for an image
     *
     * @param image the image being voted on
     * @param isUp  if the vote is up or not.
     */
    @Override
    public void voteForImage(Image image, boolean isUp, User user) {
        achievementService.voteAchievement(user, image);
        votesRepository.castVote(image, isUp, user);
    }

    /**
     * Gets an optional encouragement for a number of images
     */
    @Override
    public Optional<Encouragement> getEncouragementForNumberOfImages(int number) {
        return encouragementRepository.getEncouragementForNumberOfImages(number);
    }

    /**
     * Gets all the sample images to show
     */
    @Override
    public List<SampleImage> getSampleImages() {
        return sampleImagesRepository.getSampleImages();
    }

    @Override
    public void addImage(String path, Optional<Boolean> knownGood, List<String> additionalImagePaths) {
        imagesRepository.addImage(path, knownGood.orElse(null), additionalImagePaths);
    }
}
