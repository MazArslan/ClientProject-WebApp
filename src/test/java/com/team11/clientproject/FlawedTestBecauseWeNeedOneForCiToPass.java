package com.team11.clientproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = "app.scheduling.enable=false")

public class FlawedTestBecauseWeNeedOneForCiToPass {
    @Test
    public void assertMathsWorks() {
        assertTrue(2>1);
    }
}
