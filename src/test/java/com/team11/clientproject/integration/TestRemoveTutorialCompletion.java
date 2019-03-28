package com.team11.clientproject.integration;

import com.team11.clientproject.dtos.Tutorial;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.TutorialCompletionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")
public class TestRemoveTutorialCompletion {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private TutorialCompletionRepository tutorialCompletionRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Test
    @WithMockUser(roles = "USER", username = "user")
    public void randomTest() throws Exception {
        jdbcTemplate.update("INSERT INTO tutorial_completion (FK_user_id, FK_tutorial_id) VALUES(2,2) ");
        mvc.perform(post("/api/tutorial/removeCompletion")
                .param("name", "SAMPLE_IMAGES")
        )
                .andExpect(status().isOk());
        jdbcTemplate.queryForObject("SELECT COUNT(*) FROM tutorial_completion WHERE FK_tutorial_id=2 AND FK_user_id=2", Integer.class);

    }
}
