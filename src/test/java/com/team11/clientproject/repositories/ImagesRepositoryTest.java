package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Image;
import com.team11.clientproject.dtos.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")

public class ImagesRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testRandomImage() {
        var user = new User();
        user.setId(1);
        assertNotNull(imagesRepository.getImage(user));
    }

    @Test
    public void testSpecificImage() {
        Optional<Image> imageById = this.imagesRepository.getImageById(11);
        assertEquals(imageById.get().getPath(), "/images/10014_1.jpeg");
    }

    @Test
    public void testImageList() {
        var images = imagesRepository.getAllImages();
        assertTrue(images.size() != 0);
    }

    @Test
    public void testImageRanking() {
        usersRepository.updateTrustworthiness();
        var image = new Image();

        image.setId(11);
        var ranking = imagesRepository.getImageRanking(image);
        assertTrue(Double.compare(ranking, 0) != 0);
    }

    @Test
    @Transactional
    public void testAddingImage() {
        imagesRepository.addImage("test", null, new ArrayList<>() {{
            add("image1");
            add("image2");
        }});
        jdbcTemplate.query("SELECT * FROM images WHERE image_id=(SELECT MAX(image_id) FROM images) LIMIT 1", (rs, i) -> {
            assertEquals(rs.getString("path"), "test");
            assertEquals(rs.getObject("known_status"), null);
            assertEquals(rs.getString("additional_image_paths"), "[\"image1\", \"image2\"]");
            return true;
        });
    }

    @Test
    @Transactional
    public void testAddingImageWithTrustworthiness() {
        imagesRepository.addImage("test", true, new ArrayList<>() {{
            add("image1");
            add("image2");
        }});
        jdbcTemplate.query("SELECT * FROM images WHERE image_id=(SELECT MAX(image_id) FROM images) LIMIT 1", (rs, i) -> {
            assertEquals(rs.getString("path"), "test");
            assertEquals(rs.getObject("known_status"), true);
            assertEquals(rs.getString("additional_image_paths"), "[\"image1\", \"image2\"]");

            return true;
        });
    }

    @Test
    @Transactional
    public void testImagesListAdmin() {
        assertTrue(imagesRepository.getAllImagesDescriptions().size() != 0);
    }
}
