package com.team11.clientproject.services;

import com.pusher.rest.Pusher;
import com.team11.clientproject.dtos.AchievementDTO;
import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.UsersRepository;
import com.team11.clientproject.repositories.VotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementServiceImpl implements AchievementService {
    private final Pusher pusher;
    private final VotesRepository votesRepository;

    @Autowired
    public AchievementServiceImpl(Pusher pusher, VotesRepository votesRepository) {
        this.pusher = pusher;
        this.votesRepository = votesRepository;
    }

    @Override
    public void voteAchievement(User user, Image image) {
        pusher.trigger("votes-tracker", "vote", user);
        var votesByUser = votesRepository.votesByUser(user);
        var votesByImage = votesRepository.votesForImage(image);
        // ref: pusher documentation on account creation
        // accessed on 1/12/2018
        if (votesByUser % 5 == 0) {
            var achievement = new AchievementDTO();
            achievement.setText(user.getUsername() + " just got " + votesByUser + " votes!");
            pusher.trigger("votes-tracker", "achievement", achievement);
        } else if (votesByUser == 1) {
            var achievement = new AchievementDTO();
            achievement.setText(user.getUsername() + " just voted for the first time!");
            pusher.trigger("votes-tracker", "achievement", achievement);
        } else if (votesByImage % 5 == 0) {
            var achievement = new AchievementDTO();
            achievement.setText("This image just got " + votesByImage + " votes");
            achievement.setImage(image.getPath());
        }

    }
}
