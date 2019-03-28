package com.team11.clientproject.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")
public class EncouragementRepositoryTest {
    @Autowired
    private EncouragementRepository encouragementRepository;

    @Test
    public void testNonexistentImage() {
        var rs = encouragementRepository.getEncouragementForNumberOfImages(0);
        assertFalse(rs.isPresent());
    }

    @Test
    public void testExistingImage() {
        var rs = encouragementRepository.getEncouragementForNumberOfImages(5);
        assertTrue(rs.isPresent());
    }
}
