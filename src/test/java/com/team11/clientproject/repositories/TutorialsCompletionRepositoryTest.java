package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Tutorial;
import com.team11.clientproject.dtos.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")

public class TutorialsCompletionRepositoryTest {
    @Autowired
    private TutorialCompletionRepository tutorialCompletionRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Test
    public void testAddCompletion() {
        var user = new User();
        user.setId(1);
        var tutorial = new Tutorial();
        tutorial.setId(1);
        tutorialCompletionRepository.saveUserTutorial(user, tutorial, null);
        jdbcTemplate.query("SELECT * FROM tutorial_completion ORDER BY completed_on DESC limit 1",
                (rs, i) -> {
                    assertEquals(rs.getInt("FK_user_id"), 1);
                    assertEquals(rs.getInt("FK_tutorial_id"), 1);
                    return true;
                });
    }

    @Transactional
    @Test
    public void testAddCompletionScore() {
        var user = new User();
        user.setId(1);
        var tutorial = new Tutorial();
        tutorial.setId(1);

        tutorialCompletionRepository.saveUserTutorial(user, tutorial, 5);
        jdbcTemplate.query("SELECT * FROM tutorial_completion ORDER BY completed_on DESC limit 1",
                (rs, i) -> {
                    assertEquals(rs.getInt("FK_user_id"), 1);
                    assertEquals(rs.getInt("FK_tutorial_id"), 1);
                    assertEquals(rs.getInt("score"), 5);
                    return true;
                });
    }

    @Transactional
    @Test
    public void testRetrieveCompletion() {
        var user = new User();
        user.setId(1);
        var tutorial = new Tutorial();
        tutorial.setId(1);
        tutorial.setLastUpdatedOn(LocalDateTime.MIN);
        tutorialCompletionRepository.saveUserTutorial(user, tutorial, null);
        assertTrue(tutorialCompletionRepository.hasUserFinishedTutorial(user, tutorial));
    }

    @Transactional
    @Test
    public void testChangeCompletion() {
        var user = new User();
        user.setId(1);
        var tutorial = new Tutorial();
        tutorial.setId(1);
        tutorial.setLastUpdatedOn(LocalDateTime.MIN);
        tutorialCompletionRepository.saveUserTutorial(user, tutorial, null);
        assertTrue(tutorialCompletionRepository.hasUserFinishedTutorial(user, tutorial));
        tutorialCompletionRepository.removeUserTutorial(user, tutorial);
        assertFalse(tutorialCompletionRepository.hasUserFinishedTutorial(user, tutorial));

    }
}
