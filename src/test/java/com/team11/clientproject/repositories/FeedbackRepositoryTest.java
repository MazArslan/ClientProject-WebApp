package com.team11.clientproject.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")

public class FeedbackRepositoryTest {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testAddFeedback() {
        feedbackRepository.saveFeedback(3);
        jdbcTemplate.query("SELECT * FROM feedback ORDER BY feedback_id DESC LIMIT 1", (rs, i) -> {
            assertEquals(rs.getInt("question_1"), 3);
            return false;
        });


    }
}
