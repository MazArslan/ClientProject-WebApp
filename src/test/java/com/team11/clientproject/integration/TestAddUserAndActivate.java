package com.team11.clientproject.integration;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")

public class TestAddUserAndActivate {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    // We don't actually want to send out emails so we mock this
    @MockBean
    private JavaMailSenderImpl javaMailSender;


    @Test
    @Transactional
    public void testRegisterAndActivate() throws Exception {
        jdbcTemplate.update("UPDATE configuration SET value=1 WHERE name='email_authentication'");
        mvc.perform(post("/register")
                .param("username", "user1")
                .param("firstName", "fname")
                .param("lastName", "lname")
                .param("emailAddress", "test1@test.com")
                .param("password", "123456"))
                .andExpect(status().isOk());
        var uuid = jdbcTemplate.queryForObject("SELECT activation_uuid FROM users ORDER BY user_id DESC LIMIT 1", String.class);
        mvc.perform(get("/activate/" + uuid))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Successfully created account. Feel")));
        ;
        verify(javaMailSender).send(any(SimpleMailMessage.class));
        var enabled = jdbcTemplate.queryForObject("SELECT enabled FROM users ORDER BY user_id DESC LIMIT 1", Boolean.class);
        assertTrue(enabled);
    }

}
