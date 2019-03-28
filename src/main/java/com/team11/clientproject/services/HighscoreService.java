package com.team11.clientproject.services;

import com.team11.clientproject.dtos.Highscore;
import com.team11.clientproject.dtos.User;

import java.util.List;

public interface HighscoreService {
    List<Highscore> getHighscoresByUser(User user);
}
