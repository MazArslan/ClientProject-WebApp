package com.team11.clientproject.services;

import com.team11.clientproject.dtos.Tutorial;
import com.team11.clientproject.dtos.User;

public interface TutorialsService {
    Tutorial getTutorialByName(String name);

    boolean hasUserCompletedTutorial(User user, Tutorial tutorial);

    void saveUserTutorialCompletion(User user, Tutorial tutorial, Integer score);

    void removeTutorialCompletion(User user, Tutorial tutorial);
}
