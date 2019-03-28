package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Stores/retrieves votes
 */
@Service
public class VotesRepositoryImpl implements VotesRepository {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public VotesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Casts a vote for an image
     *
     * @param image the image being voted on
     * @param isUp  if the vote is up or not
     */
    @Override
    public void castVote(Image image, boolean isUp, User user) {
        jdbcTemplate.update("INSERT INTO votes(is_vote_up,FK_image_id, FK_user_id)" +
                "VALUES(?,?,?)", new Object[]{isUp, image.getId(), user.getId()});
    }

    @Override
    public int votesForImage(Image image) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM votes WHERE FK_image_id=?", new Object[]{image.getId()}, Integer.class);
    }

    @Override
    public int votesByUser(User user) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM votes WHERE FK_user_id=?", new Object[]{user.getId()}, Integer.class);
    }
}
