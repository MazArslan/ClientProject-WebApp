package com.team11.clientproject.services;

import com.team11.clientproject.dtos.Highscore;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.HighscoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HighscoreServiceImpl implements HighscoreService {
    private final HighscoreRepository highscoreRepository;

    @Autowired
    public HighscoreServiceImpl(HighscoreRepository highscoreRepository) {
        this.highscoreRepository = highscoreRepository;
    }

    /**
     * Gets all the highscores to be shown for a user
     */
    @Override
    public List<Highscore> getHighscoresByUser(User user) {
        return highscoreRepository.getHighscoreBoardByUser(user);
    }
}
