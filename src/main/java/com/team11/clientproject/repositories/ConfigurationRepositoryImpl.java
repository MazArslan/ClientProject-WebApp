package com.team11.clientproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigurationRepositoryImpl implements ConfigurationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ConfigurationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getConfiguration(String key) {
        return jdbcTemplate.queryForObject("SELECT value FROM configuration WHERE name=?", new Object[]{key}, Integer.class);
    }
}
