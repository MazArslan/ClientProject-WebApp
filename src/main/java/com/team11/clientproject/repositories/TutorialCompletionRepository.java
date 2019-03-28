package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Tutorial;
import com.team11.clientproject.dtos.User;
import org.springframework.lang.Nullable;

public interface TutorialCompletionRepository {
    boolean hasUserFinishedTutorial(User user, Tutorial tutorial);

    void saveUserTutorial(User user, Tutorial tutorial, @Nullable Integer score);

    void removeUserTutorial(User user, Tutorial tutorial);
}
