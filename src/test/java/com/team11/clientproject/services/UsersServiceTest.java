package com.team11.clientproject.services;

import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
@TestPropertySource(properties = "app.scheduling.enable=false")
public class UsersServiceTest {
    @MockBean
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;

    // needs to be the implementation as spring boot is a piece of shit
    // and doesn't follow its own convention of using the interface instead of the implementation
    // cunt.
    @MockBean
    private JavaMailSenderImpl javaMailSender;

    @MockBean
    private ConfigurationService configurationService;


    @Test
    public void testActivation() {
        var user = Mockito.mock(User.class);
        when(usersRepository.findUserByActivationUUID("123")).thenReturn(Optional.of(user));
        assertTrue(usersService.activateUser("123"));
        verify(usersRepository).findUserByActivationUUID("123");
        verify(usersRepository).activateUser(user);
    }

    @Test
    public void testActivateNotReal() {
        when(usersRepository.findUserByActivationUUID("123")).thenReturn(Optional.empty());
        assertFalse(usersService.activateUser("123"));
        verify(usersRepository).findUserByActivationUUID("123");
    }

    @Test
    public void testRegisterSendsEmail() {
        var user = new User();
        when(configurationService.getConfiguration(anyString())).thenReturn(1);
        user.setEmail("test@test.com");
        usersService.registerUser(user);
        verify(usersRepository).registerUser(user);
        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testRegisterEmailDisabled() {
        var user = new User();
        when(configurationService.getConfiguration(anyString())).thenReturn(0);
        user.setEmail("test@test.com");
        usersService.registerUser(user);
        verify(usersRepository).registerUser(user);
        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }
}
