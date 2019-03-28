package com.team11.clientproject.services;

import com.team11.clientproject.dtos.User;
import com.team11.clientproject.dtos.UserTrustworthiness;

import java.util.List;

public interface UsersService {
    void registerUser(User user);

    User getUserByUsername(String username);

    List<UserTrustworthiness> getAllUserTrustworthiness();

    boolean activateUser(String activationUUID);
}
