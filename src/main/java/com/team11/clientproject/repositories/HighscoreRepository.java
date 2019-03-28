package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Highscore;
import com.team11.clientproject.dtos.User;

import java.util.List;

public interface HighscoreRepository {
    List<Highscore> getHighscoreBoardByUser(User user);

    void updateMaterialisedView();
}
