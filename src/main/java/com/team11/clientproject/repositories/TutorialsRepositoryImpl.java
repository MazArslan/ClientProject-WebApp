package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Tutorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Stores data related to the tutorials table
 */
@Repository
public class TutorialsRepositoryImpl implements TutorialsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TutorialsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Tutorial> rowMapper() {
        return (rs, i) -> {
            var tutorial = new Tutorial();
            tutorial.setId(rs.getInt("tutorial_id"));
            tutorial.setName(rs.getString("tutorial_name"));
            // reference: https://stackoverflow.com/questions/23263490/how-to-convert-java-sql-timestamp-to-localdate-java8-java-time
            // accessed 23/11/2018
            tutorial.setLastUpdatedOn(rs.getTimestamp("last_updated_on").toLocalDateTime());
            return tutorial;
        };
    }

    @Override
    public Optional<Tutorial> findByName(String name) {
        return jdbcTemplate.query("SELECT * from tutorials WHERE tutorial_name=?", new Object[]{name}, rowMapper()).stream().findFirst();
    }


}
