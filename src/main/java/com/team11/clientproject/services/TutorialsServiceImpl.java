package com.team11.clientproject.services;

import com.team11.clientproject.dtos.Tutorial;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.TutorialCompletionRepository;
import com.team11.clientproject.repositories.TutorialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * Tutorials service - deals with tutorial completion.
 */
@Service
public class TutorialsServiceImpl implements TutorialsService {

    private final TutorialsRepository tutorialsRepository;

    private final TutorialCompletionRepository tutorialCompletionRepository;

    @Autowired
    public TutorialsServiceImpl(TutorialsRepository tutorialsRepository, TutorialCompletionRepository tutorialCompletionRepository) {
        this.tutorialsRepository = tutorialsRepository;
        this.tutorialCompletionRepository = tutorialCompletionRepository;
    }

    /**
     * Gets a tutorial by name
     *
     * @param name the name of the tutorial
     * @return the tutorial
     */
    @Override
    public Tutorial getTutorialByName(String name) {
        return tutorialsRepository.findByName(name).orElseThrow();
    }

    /**
     * Checks if the user has completed a  tutorial
     * @param user the user
     * @param tutorial the  tutorial
     * @return true/false depending on the user
     */
    @Override
    public boolean hasUserCompletedTutorial(User user, Tutorial tutorial) {
        return tutorialCompletionRepository.hasUserFinishedTutorial(user, tutorial);
    }

    /**
     * Saves that  a user has completed a tutorial
     * @param user the suer
     * @param tutorial the tutorial
     * @param score the score - intentially nullable
     */
    @Override
    public void saveUserTutorialCompletion(User user, Tutorial tutorial, @Nullable Integer score) {
        tutorialCompletionRepository.saveUserTutorial(user, tutorial, score);
    }

    @Override
    public void removeTutorialCompletion(User user, Tutorial tutorial) {
        tutorialCompletionRepository.removeUserTutorial(user, tutorial);
    }
}
