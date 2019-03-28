package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.User;

public interface VotesRepository {
    void castVote(Image image, boolean isUp, User user);

    int votesForImage(Image image);

    int votesByUser(User user);
}
