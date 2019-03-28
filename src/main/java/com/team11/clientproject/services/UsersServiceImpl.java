package com.team11.clientproject.services;

import com.team11.clientproject.dtos.User;
import com.team11.clientproject.dtos.UserTrustworthiness;
import com.team11.clientproject.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Deals with user logic.
 */
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final JavaMailSender javaMailSender;
    private final ConfigurationService configurationService;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, JavaMailSender javaMailSender, ConfigurationService configurationService) {
        this.usersRepository = usersRepository;
        this.javaMailSender = javaMailSender;
        this.configurationService = configurationService;
    }

    @Async
    @Override
    public void registerUser(User user) {

        if (configurationService.getConfiguration(ConfigurationService.EMAIL_CONFIGURATION) == 1) {
            var activationUUID = UUID.randomUUID().toString();
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("no-reply@swiperightforbrainscience.com");
            message.setTo(user.getEmail());
            message.setText("Hi! Please activate your account at http://localhost:8080/activate/" + activationUUID);
            message.setSubject("Welcome to Swipe right for brain science!");
            javaMailSender.send(message);
            user.setActivationUUID(activationUUID);
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        usersRepository.registerUser(user);
    }

    /**
     * Gets a user by their username
     *
     * @param username the username
     * @return the user.
     */
    @Override
    public User getUserByUsername(String username) {
        return usersRepository.findUserByUsername(username).get();
    }

    /**
     * Gets the trustworthiness for all users
     *
     * @return the trustworthiness
     */
    @Override
    public List<UserTrustworthiness> getAllUserTrustworthiness() {
        return usersRepository.getUserTrustworthinessForAllUsers();
    }

    /**
     * Activates a user based on their activaton UUID
     *
     * @param activationUUID the users activation UUID
     * @return the success/ fail status.
     */
    @Override
    public boolean activateUser(String activationUUID) {
        var user = usersRepository.findUserByActivationUUID(activationUUID);
        if (user.isPresent()) {
            usersRepository.activateUser(user.get());
            return true;
        }
        return false;
    }
}
