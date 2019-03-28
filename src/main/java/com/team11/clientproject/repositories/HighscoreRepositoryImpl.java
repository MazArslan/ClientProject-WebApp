package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Highscore;
import com.team11.clientproject.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HighscoreRepositoryImpl implements HighscoreRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HighscoreRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Contains the mapper to the highscore object
     */
    private RowMapper<Highscore> highscoreRowMapper() {
        return (rs, i) -> {
            var highscore = new Highscore();
            highscore.setScore(rs.getInt("highscore"));
            highscore.setUsername(rs.getString("username"));
            return highscore;
        };
    }

    /**
     * Gets  the highscore board for an individual user
     */
    @Override
    public List<Highscore> getHighscoreBoardByUser(User user) {
        return jdbcTemplate.query("CALL get_scoring_chart(?)", new Object[]{user.getId()}, highscoreRowMapper());
    }

    /**
     * Updates the materalised view.
     */
    @Override
    public void updateMaterialisedView() {
        jdbcTemplate.update("CALL update_scoring_chart()");
    }
}
