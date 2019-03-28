package com.team11.clientproject.repositories;

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

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")

public class HighscoreRepositoryTests {
    @Autowired
    private HighscoreRepository highscoreRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    public void testHighscoreChanges() {
        highscoreRepository.updateMaterialisedView();
        var user = new User();
        user.setId(1);
        var highscores = highscoreRepository.getHighscoreBoardByUser(user);
        var userscore1 = highscores.stream().filter((entry) -> {
            return entry.getUsername().equals("admin");
        }).findFirst().get();
        jdbcTemplate.update("INSERT INTO votes(FK_image_id,FK_user_id,is_vote_up) VALUES(11,1,1)");
        var highscores2 = highscoreRepository.getHighscoreBoardByUser(user);
        var userscore2 = highscores2.stream().filter((entry) -> {
            return entry.getUsername().equals("admin");
        }).findFirst().get();
        assertEquals(userscore1.getScore(), userscore2.getScore() - 1);
    }
}
