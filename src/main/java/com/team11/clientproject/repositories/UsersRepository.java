package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.User;
import com.team11.clientproject.dtos.UserTrustworthiness;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    Optional<User> findUserByUsername(String username);

    void registerUser(User user);

    void updateTrustworthiness();

    List<UserTrustworthiness> getUserTrustworthinessForAllUsers();

    Optional<User> findUserByActivationUUID(String activationUUID);

    void activateUser(User user);
}
