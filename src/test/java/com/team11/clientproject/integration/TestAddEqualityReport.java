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

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")

public class TestAddEqualityReport {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @WithMockUser(authorities = "ROLE_USER")
    public void testAddEquality() throws Exception {
        mvc.perform(post("/equality")
                .param("gender", "male")
                .param("ethnicity", "white")
                .param("country", "United Kingdom")
                .param("postcodeStart", "CF10")
                .param("sexuality", "straight"))
                .andExpect(status().is3xxRedirection());
        jdbcTemplate.queryForObject("SELECT * FROM equality_diversity ORDER BY equality_diversity_id DESC LIMIT 1", (rs, i) -> {
            assertEquals(rs.getString("country"), "United Kingdom");
            assertEquals(rs.getString("ethnicity"), "white");
            assertEquals(rs.getString("gender"), "male");
            assertEquals(rs.getString("postcode_start"), "CF10");
            assertEquals(rs.getString("sexuality"), "straight");
            return true;
        });


    }



}
