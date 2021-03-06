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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")
public class TestAddFeedback {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void submitFeedBackTest() throws Exception {
        mvc.perform(post("/feedback")
                .param("question1", "3")).andExpect(status().isOk());
        jdbcTemplate.query("SELECT * FROM feedback ORDER BY feedback_id DESC LIMIT 1", (rs, i) -> {
            assertEquals(rs.getInt("question_1"), 3);
            return false;
        });

    }


}

