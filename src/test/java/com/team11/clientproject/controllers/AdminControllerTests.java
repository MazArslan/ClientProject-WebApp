package com.team11.clientproject.controllers;

import com.team11.clientproject.dtos.AdminImageDTO;
import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.ImageDescription;
import com.team11.clientproject.dtos.UserTrustworthiness;
import com.team11.clientproject.services.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")

public class AdminControllerTests {
    @MockBean
    private UsersService usersService;
    @MockBean
    private ImagesService imagesService;
    @MockBean
    private VotingService votingService;
    @MockBean
    private StorageService storageService;
    @MockBean
    private ExportDataService exportDataService;
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDataShows() throws Exception {
        var userTrustworthiness = new LinkedList<UserTrustworthiness>() {{
            add(new UserTrustworthiness(1, "admin", 3.0));
        }};
        when(usersService.getAllUserTrustworthiness()).thenReturn(userTrustworthiness);
        mvc.perform(get("/admin/stats/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("3")))
                .andExpect(content().string(containsString("admin")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testImageDisplay() throws Exception {
        var imageList = new LinkedList<ImageDescription>() {{
            add(new ImageDescription(1, "/test", "GOOD", true));
        }};
        when(imagesService.getImagesDescription()).thenReturn(imageList);
        mvc.perform(get("/admin/stats/images"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1")))
                .andExpect(content().string(containsString("Known good")))
                .andExpect(content().string(containsString("/test")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testImageUploadNotKnown() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "filename.jpg", "image/jpeg", "lol its a jpg".getBytes());
        MockMultipartFile additional1 = new MockMultipartFile("additional", "filename.jpg", "image/jpeg", "lol its a jpg".getBytes());
        MockMultipartFile additional2 = new MockMultipartFile("additional", "filename123.jpg", "image/jpeg", "lol its a jpg".getBytes());

        when(storageService.isImage(any())).thenReturn(true);
        when(storageService.persistImage(any())).thenReturn("pathypath");
        mvc.perform(multipart("/admin/stats/images/upload").file(image).file(additional1).file(additional2)
                .param("trustworthy", ""))
                .andExpect(status().is3xxRedirection());
        verify(storageService, times(3)).persistImage(any());
        verify(votingService).addImage("pathypath", Optional.empty(), new ArrayList<>() {{
            add("pathypath");
            add("pathypath");
        }});
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testImageUploadKnown() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "filename.jpg", "image/jpeg", "lol its a jpg".getBytes());
        MockMultipartFile additional1 = new MockMultipartFile("additional", "filename.jpg", "image/jpeg", "lol its a jpg".getBytes());
        MockMultipartFile additional2 = new MockMultipartFile("additional", "filename123.jpg", "image/jpeg", "lol its a jpg".getBytes());

        when(storageService.isImage(any())).thenReturn(true);
        when(storageService.persistImage(any())).thenReturn("pathypath");
        mvc.perform(multipart("/admin/stats/images/upload").file(image).file(additional1).file(additional2)
                .param("trustworthy", "TRUE"))
                .andExpect(status().is3xxRedirection());
        verify(storageService, times(3)).persistImage(any());
        verify(votingService).addImage("pathypath", Optional.of(true), new ArrayList<>() {{
            add("pathypath");
            add("pathypath");
        }});
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSingleImageDisplay() throws Exception {
        var imagedto = new AdminImageDTO(0.534, "/test", 2);
        var image = new Image();
        when(votingService.getImageById(1)).thenReturn(Optional.of(image));
        when(imagesService.getAdminImageDTO(any())).thenReturn(imagedto);
        mvc.perform(get("/admin/stats/images/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("0.534")))
                .andExpect(content().string(containsString("/test")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDownload() throws Exception {
        when(exportDataService.getAllDatabaseInformation()).thenReturn(new LinkedList<>());
        mvc.perform(get("/admin/stats/download"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }
}
