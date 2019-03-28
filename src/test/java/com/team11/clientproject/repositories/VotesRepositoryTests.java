package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Image;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")

public class VotesRepositoryTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private VotesRepository votesRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Test
    @Transactional
    public void testAddVote() {
        var image = new Image();
        image.setId(11);
        var user = usersRepository.findUserByUsername("user").get();
        this.votesRepository.castVote(image, true, user);
        jdbcTemplate.queryForObject("SELECT * FROM votes ORDER BY vote_id DESC LIMIT 1", (rs, i) -> {
            assertEquals(rs.getInt("FK_image_id"), 11);
            assertEquals(rs.getBoolean("is_vote_up"), true);
            assertEquals(rs.getInt("FK_user_id"), user.getId());
            return true;
        });

    }


}
