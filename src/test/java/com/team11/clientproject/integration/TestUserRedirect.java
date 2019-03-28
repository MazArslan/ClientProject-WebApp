package com.team11.clientproject.integration;


import com.team11.clientproject.utils.AuthenticationHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
public class TestUserRedirect {

    @Autowired
    MockMvc mvc;
    @Test
    public void testUserNotCompletedFeedback() throws Exception {
        mvc.perform(post("/login")
                .param("username", "user")
                .param("password", "123456"))
                .andDo(print())
                .andExpect(redirectedUrl("/tutorial"));


    }
}
