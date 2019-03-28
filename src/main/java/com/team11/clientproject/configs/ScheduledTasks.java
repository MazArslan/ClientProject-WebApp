package com.team11.clientproject.configs;

import com.team11.clientproject.repositories.TrackUserLoginRepository;
import com.team11.clientproject.repositories.HighscoreRepository;
import com.team11.clientproject.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);
    private final UsersRepository usersRepository;
    private TrackUserLoginRepository trackUserLoginRepository;
    private final HighscoreRepository highscoreRepository;


    @Autowired
    public ScheduledTasks(UsersRepository usersRepository, TrackUserLoginRepository trackUserLoginRepository, HighscoreRepository highscoreRepository) {
        this.usersRepository = usersRepository;
        this.trackUserLoginRepository = trackUserLoginRepository;
        this.highscoreRepository = highscoreRepository;
    }


    @Scheduled(fixedDelay = 3000)
    public void updateTrustworthiness() {
        LOG.info("Updating trustworthiness");
        usersRepository.updateTrustworthiness();
    }


    @Scheduled(fixedDelay = 300000, initialDelay = 50)
    public void updateScoreboard() {
        LOG.info("Updating scoreboard manually");
        highscoreRepository.updateMaterialisedView();
    }
}
