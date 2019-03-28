package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.User;

public interface TrackUserLoginRepository {
    void userLogin(User user);

    void userLogout(User user);

    void sessionExpired();
}
