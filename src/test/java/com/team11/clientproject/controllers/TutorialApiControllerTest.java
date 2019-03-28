package com.team11.clientproject.controllers;

import com.team11.clientproject.dtos.Tutorial;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.TutorialsRepository;
import com.team11.clientproject.services.TutorialsService;
import com.team11.clientproject.services.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")

public class TutorialApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorialsService tutorialsService;
    @MockBean
    private UsersService usersService;

    @Test
    @WithMockUser(roles = "USER", username = "user")
    public void testIsTutorialComplete() throws Exception {
        var mockUser = Mockito.mock(User.class);
        var mockTutorial = Mockito.mock(Tutorial.class);
        when(usersService.getUserByUsername(any())).thenReturn(mockUser);
        when(tutorialsService.hasUserCompletedTutorial(any(), any())).thenReturn(true);
        when(tutorialsService.getTutorialByName("EXAMPLE_TUTORIAL")).thenReturn(mockTutorial);
        mvc.perform(get("/api/tutorial/hasCompleted")
                .param("name", "EXAMPLE_TUTORIAL")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        verify(tutorialsService).hasUserCompletedTutorial(mockUser, mockTutorial);
    }

    @Test
    @WithMockUser(roles = "USER", username = "user")
    public void testCompleteTutorial() throws Exception {
        var mockUser = Mockito.mock(User.class);
        var mockTutorial = Mockito.mock(Tutorial.class);
        when(usersService.getUserByUsername(any())).thenReturn(mockUser);
        when(tutorialsService.getTutorialByName("EXAMPLE_TUTORIAL")).thenReturn(mockTutorial);
        mvc.perform(post("/api/tutorial/complete")
                .param("name", "EXAMPLE_TUTORIAL")
        )
                .andExpect(status().isOk());
        verify(tutorialsService).saveUserTutorialCompletion(mockUser, mockTutorial, null);
    }

    @Test
    @WithMockUser(roles = "USER", username = "user")
    public void testCompleteTutorialWithScore() throws Exception {
        var mockUser = Mockito.mock(User.class);
        var mockTutorial = Mockito.mock(Tutorial.class);
        when(usersService.getUserByUsername(any())).thenReturn(mockUser);
        when(tutorialsService.getTutorialByName("EXAMPLE_TUTORIAL")).thenReturn(mockTutorial);
        mvc.perform(post("/api/tutorial/complete")
                .param("name", "EXAMPLE_TUTORIAL")
                .param("score", "5")
        )
                .andExpect(status().isOk());
        verify(tutorialsService).saveUserTutorialCompletion(mockUser, mockTutorial, 5);
    }

    @Test
    @WithMockUser(roles = "USER", username = "user")
    public void testUndoTutorial() throws Exception {
        var mockUser = Mockito.mock(User.class);
        var mockTutorial = Mockito.mock(Tutorial.class);
        when(usersService.getUserByUsername(any())).thenReturn(mockUser);
        when(tutorialsService.getTutorialByName("EXAMPLE_TUTORIAL")).thenReturn(mockTutorial);
        mvc.perform(post("/api/tutorial/removeCompletion")
                .param("name", "EXAMPLE_TUTORIAL")
        )
                .andExpect(status().isOk());
        verify(tutorialsService).removeTutorialCompletion(mockUser, mockTutorial);
    }
}
