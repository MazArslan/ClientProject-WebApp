package com.team11.clientproject.services;

import com.pusher.rest.Pusher;
import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.VotesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")

public class AchievementServiceTest {
    @MockBean
    private Pusher pusher;
    @MockBean
    private VotesRepository votesRepository;
    @Autowired
    private AchievementService achievementService;

    @Test
    public void testAchievement() {
        var image = Mockito.mock(Image.class);
        var user = Mockito.mock(User.class);
        when(votesRepository.votesByUser(user)).thenReturn(9);
        when(votesRepository.votesForImage(image)).thenReturn(9);
        achievementService.voteAchievement(user, image);
        verify(pusher, times(1)).trigger(anyString(), any(), any());

    }

    @Test
    public void testAchievementWithCorrectNumber() {
        var image = Mockito.mock(Image.class);
        var user = Mockito.mock(User.class);
        when(votesRepository.votesByUser(user)).thenReturn(10);
        when(votesRepository.votesForImage(image)).thenReturn(3);
        achievementService.voteAchievement(user, image);
        verify(pusher, times(2)).trigger(anyString(), any(), any());

    }

}
