package com.team11.clientproject.controllers;

import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.services.EqualityDiversityService;
import com.team11.clientproject.services.TutorialsService;
import com.team11.clientproject.services.UsersService;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")

public class UserControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsersService usersService;

    @MockBean
    private TutorialsService tutorialsService;

    @MockBean
    private EqualityDiversityService equalityDiversityService;


    @Test
    public void testRegisterValid() throws Exception {
        mvc.perform(post("/register")
                .param("username", "user1")
                .param("firstName", "fname")
                .param("lastName", "lname")
                .param("emailAddress", "test1@test.com")
                .param("password", "123456"))
                .andExpect(status().isOk());
        verify(usersService).registerUser(any());
    }

    @Test
    public void testRegisterInvalid() throws Exception {
        mvc.perform(post("/register"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRedirectCorrect() throws Exception {
        when(equalityDiversityService.findUserCompletion(any())).thenReturn(true);
        when(tutorialsService.hasUserCompletedTutorial(any(), any())).thenReturn(true);
        mvc.perform(post("/login")
                .param("username", "user")
                .param("password", "123456"))
                .andDo(print())
                .andExpect(redirectedUrl("/vote/do"));
    }

    @Test
    public void testRedirectNotFilledIn() throws Exception {
        when(equalityDiversityService.findUserCompletion(any())).thenReturn(false);
        mvc.perform(post("/login")
                .param("username", "user")
                .param("password", "123456"))
                .andDo(print())
                .andExpect(redirectedUrl("/equality"));
    }

    @Test
    public void testActivateUser() throws Exception {
        when(usersService.activateUser(any())).thenReturn(true);
        mvc.perform(get("/activate/uuidlol"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Successfully created account. Feel")));
    }

    @Test
    public void testActivateUserInvalid() throws Exception {
        when(usersService.activateUser(any())).thenReturn(false);
        mvc.perform(get("/activate/uuidlol"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Something went wrong")));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER", username = "user")

    public void testAccessTutorial() throws Exception {
        when(tutorialsService.hasUserCompletedTutorial(any(), any())).thenReturn(true);
        mvc.perform(get("/tutorial"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER", username = "user")
    public void testAccessTutorialSuccess() throws Exception {
        when(tutorialsService.hasUserCompletedTutorial(any(), any())).thenReturn(false);
        mvc.perform(get("/tutorial"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER", username = "user")
    public void testFinishTutorial() throws Exception {
        mvc.perform(post("/tutorial"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        verify(tutorialsService).saveUserTutorialCompletion(any(), any(), any());
    }
}
