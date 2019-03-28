package com.team11.clientproject.services;

import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.VotesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;


@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")

public class VotingServiceTest {
    @MockBean
    private VotesRepository votesRepository;
    @MockBean
    private AchievementService achievementService;
    @Autowired
    private VotingService votingService;

    @Test
    public void testAddVote() {
        var image = Mockito.mock(Image.class);
        var isUp = true;
        var user = Mockito.mock(User.class);
        votingService.voteForImage(image, isUp, user);
        verify(votesRepository).castVote(image, isUp, user);
    }
}
