package com.team11.clientproject.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")

public class TutorialsRepositoryTest {
    @Autowired
    private TutorialsRepository tutorialsRepository;

    @Test
    public void testRetrieveByName() {
        var tutorial = tutorialsRepository.findByName("SAMPLE_IMAGES").get();
        assertEquals(tutorial.getId(), 2);
        assertEquals(tutorial.getName(), "SAMPLE_IMAGES");
    }

    @Test
    public void testRetrieveByNameInvalid() {
        var tutorial = tutorialsRepository.findByName("NOT_A_TUTORIAL");
        assertFalse(tutorial.isPresent());
    }


}
