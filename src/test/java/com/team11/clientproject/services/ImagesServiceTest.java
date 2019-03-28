package com.team11.clientproject.services;

import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.repositories.ImagesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")
public class ImagesServiceTest {
    @MockBean
    private ImagesRepository imagesRepository;
    @Autowired
    private ImagesService imagesService;

    @Test
    public void testImageDTOBuilder() {
        var image = new Image(1, "/path", new ArrayList<>());
        when(imagesRepository.getImageRanking(image)).thenReturn(2.34);
        var adminImage = imagesService.getAdminImageDTO(image);
        assertEquals(adminImage.getId(), 1);
        assertEquals(adminImage.getPath(), "/path");
        assertTrue(Double.compare(adminImage.getRanking(), 2.34) == 0);
    }
}
