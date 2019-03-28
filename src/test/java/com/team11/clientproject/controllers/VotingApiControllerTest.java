package com.team11.clientproject.controllers;

import com.team11.clientproject.dtos.Encouragement;
import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.SampleImage;
import com.team11.clientproject.services.VotingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")

public class VotingApiControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private VotingService votingService;

    @Test
    @WithMockUser(authorities = "ROLE_USER", username = "user")
    public void testGetImage() throws Exception {
        when(votingService.getImageToVoteOn(any())).thenReturn(new Image(6, "/images/6.png", new ArrayList<>() {{
        }}));
        when(votingService.getEncouragementForNumberOfImages(anyInt())).thenReturn(Optional.empty());
        mvc.perform(get("/api/voting/image"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":6,\"path\":\"/images/6.png\", \"additionalImages\":[]}"));
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER", username = "user")
    public void testGetEncouragement() throws Exception {
        var encouragement = new Encouragement();
        encouragement.setNumberOfImages(3);
        encouragement.setText("Brilliant Start!");
        encouragement.setPath("/images/encouragement/1.png");
        encouragement.setId(1);
        when(votingService.getEncouragementForNumberOfImages(3)).thenReturn(Optional.of(encouragement));
        mvc.perform(get("/api/voting/image")
                .param("numberOfImages", "3"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"path\":\"/images/encouragement/1.png\",\"text\":\"Brilliant Start!\",\"numberOfImages\":3}"));
    }
    @Test
    @WithMockUser(authorities = "ROLE_USER", username = "user")
    public void testAddVote() throws Exception {
        var image = new Image(1, "/images/1.png", new ArrayList<>());
        when(votingService.getImageById(1)).thenReturn(Optional.of(image));
        mvc.perform(post("/api/voting/vote/1").param("isUp", "true"))
                .andExpect(status().isOk());
        verify(votingService).voteForImage(any(), anyBoolean(), any());
    }

    @Test(expected = NestedServletException.class)
    @WithMockUser(authorities = "ROLE_USER", username = "user")
    public void testAddVoteInvalid() throws Exception {
        when(votingService.getImageById(1)).thenReturn(Optional.empty());
        mvc.perform(post("/api/voting/vote/1").param("isUp", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    public void testGetSampleImages() throws Exception {
        var image = new SampleImage(1, "/images/1.png", true, new LinkedList<>());
        var images = new LinkedList<SampleImage>() {{
            add(image);
        }};
        when(votingService.getSampleImages()).thenReturn(images);
        mvc.perform(get("/api/voting/sampleimages"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"path\":\"/images/1.png\",\"correct\":true}]"));
    }
}
