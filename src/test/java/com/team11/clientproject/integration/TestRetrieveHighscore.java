package com.team11.clientproject.integration;

import com.team11.clientproject.dtos.Highscore;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.HighscoreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")

public class TestRetrieveHighscore {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private HighscoreRepository highscoreRepository;

    @Test
    @Transactional
    @WithMockUser(roles = "USER", username = "user")
    public void testHighscoreRetrieve() throws Exception {
        highscoreRepository.updateMaterialisedView();
        mvc.perform(get("/highscore"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user")));
    }


}
