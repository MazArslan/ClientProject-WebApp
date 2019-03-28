package com.team11.clientproject.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")

public class ExportDataRepositoryTests {
    @Autowired
    private ExportDataRepository exportDataRepository;

    @Test
    public void assertInformationIsRetrieved() {
        assertTrue(exportDataRepository.getAllDatabaseInformation().size() > 0);
    }
}
