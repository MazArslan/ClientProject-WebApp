package com.team11.clientproject.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")
public class TestGetEncouragementImage {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testGetEncouragement() throws Exception {
        mvc.perform(get("/api/voting/image")
                .param("numberOfImages", "5"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"path\":\"/images/encouragement/1.png\",\"text\":\"<h1>Well done! You are halfway through</h1> <h2>The temporal lobe:</h2>Great work! You just unlocked the temporal lobe. The temporal is involved in perceiving and remembering sounds. It also works in conjunction with the hippocampus to form conscious memories. <h3>The case of Henry Molaison 1926-2008: </h3> From an early age, Henry suffered from severe epilepsy. In an effort to control his seizures, he had an operation that removed a large part of his temporal lobe and hippocampus on both sides. Whilst the surgery cured him of his epilepsy, tragically he was left unable to form new memories.   \",\"numberOfImages\":5,\"logoutButton\":false}\n"));

    }
}
