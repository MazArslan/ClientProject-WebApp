package com.team11.clientproject.controllers;

import com.team11.clientproject.services.EqualityDiversityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")

public class EqualityDiversityControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EqualityDiversityService equalityDiversityService;

    @WithMockUser(authorities = "ROLE_USER")
    @Test
    public void testAddFeedback() throws Exception {
        mvc.perform(post("/equality")
                .param("gender", "male")
                .param("ethnicity", "white")
                .param("country", "United Kingdom")
                .param("postcodeStart", "CF10")
                .param("sexuality", "straight"))
                .andExpect(status().is3xxRedirection());

        verify(equalityDiversityService).insertEntry(any());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void testAddFeedbackSome() throws Exception {
        mvc.perform(post("/equality")
                .param("gender", "")
                .param("ethnicity", "white")
                .param("country", "")
                .param("postcodeStart", "CF10")
                .param("sexuality", "straight"))
                .andExpect(status().is3xxRedirection());
        verify(equalityDiversityService).insertEntry(any());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void testAddFeedbackNone() throws Exception {
        mvc.perform(post("/equality")
                .param("gender", "")
                .param("ethnicity", "")
                .param("country", "")
                .param("postcodeStart", "")
                .param("sexuality", ""))
                .andExpect(status().is3xxRedirection());
        verify(equalityDiversityService).insertEntry(any());
    }
}
