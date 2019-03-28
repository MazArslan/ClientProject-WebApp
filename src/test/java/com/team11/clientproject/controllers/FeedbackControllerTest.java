package com.team11.clientproject.controllers;

import com.team11.clientproject.services.FeedbackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")
public class FeedbackControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private FeedbackService feedbackService;

    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void submitFeedBackTest() throws Exception {
        mvc.perform(post("/feedback")
                .param("question1", "3")).andExpect(status().isOk());
        verify(feedbackService).addFeedback(3);

    }
}
