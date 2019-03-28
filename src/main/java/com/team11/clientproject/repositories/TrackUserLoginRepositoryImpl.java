package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TrackUserLoginRepositoryImpl implements TrackUserLoginRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TrackUserLoginRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void userLogin(User user) {
        if (user != null) {
            jdbcTemplate.update("CALL startTime(?)", new Object[]{user.getId()});
        }
    }

    @Override
    public void userLogout(User user) {
        if (user != null) {

            jdbcTemplate.update("CALL endTime(?)", new Object[]{user.getId()});
        }
    }

    @Override
    public void sessionExpired() {
        jdbcTemplate.update("CALL checkUserActivity()");
    }
}
