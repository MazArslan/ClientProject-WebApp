package com.team11.clientproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackRepositoryImpl implements FeedbackRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FeedbackRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Stores a feedback entry
     */
    @Override
    public void saveFeedback(int question1) {

        jdbcTemplate.update("INSERT INTO feedback(question_1) VALUES(?)", new Object[]{question1});

    }
}
