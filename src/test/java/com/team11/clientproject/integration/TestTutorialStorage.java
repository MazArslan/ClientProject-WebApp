package com.team11.clientproject.integration;

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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")

public class TestTutorialStorage {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @WithMockUser(authorities = "ROLE_USER", username = "user")
    public void testTutorialCompletion() throws Exception {
        jdbcTemplate.update("DELETE FROM tutorial_completion");
        mvc.perform(get("/api/tutorial/hasCompleted")
                .param("name", "SAMPLE_IMAGES")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("false"));


        mvc.perform(post("/api/tutorial/complete")
                .param("name", "SAMPLE_IMAGES")
        )
                .andExpect(status().isOk());


        mvc.perform(get("/api/tutorial/hasCompleted")
                .param("name", "SAMPLE_IMAGES")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));


    }
}