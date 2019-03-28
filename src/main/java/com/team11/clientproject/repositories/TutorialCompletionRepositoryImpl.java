package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Tutorial;
import com.team11.clientproject.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class TutorialCompletionRepositoryImpl implements TutorialCompletionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TutorialCompletionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Checks if the user has finished the tutorial
     *
     * @param user     the user
     * @param tutorial the tutoral
     * @return if they have
     */
    @Override
    public boolean hasUserFinishedTutorial(User user, Tutorial tutorial) {
        return jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM tutorial_completion WHERE FK_user_id=? AND FK_tutorial_id=? AND completed_on>?",
                        new Object[]{user.getId(), tutorial.getId(), tutorial.getLastUpdatedOn()}, Integer.class) > 0;
    }

    /**
     * Saves that the user has just completed the tutoriial
     * @param user the user
     * @param tutorial the  tutorial
     */
    @Override
    public void saveUserTutorial(User user, Tutorial tutorial, @Nullable Integer score) {
        jdbcTemplate.update("INSERT INTO tutorial_completion (FK_user_id, FK_tutorial_id, score)" +
                "VALUES(?,?,?)", new Object[]{user.getId(), tutorial.getId(), score});

    }

    @Override
    public void removeUserTutorial(User user, Tutorial tutorial) {
        jdbcTemplate.update("CALL remove_user_tutorial_completion(?,?);", new Object[]{user.getId(), tutorial.getId()});
    }
}
