package com.team11.clientproject.services;

import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.User;

public interface AchievementService {
    void voteAchievement(User user, Image image);
}
